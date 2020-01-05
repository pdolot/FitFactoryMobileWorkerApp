package com.example.fitfactorymobileworkerapp.presentation.pages.verifyPass

import android.graphics.Rect
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfactorymobileworkerapp.base.BaseViewModel
import com.example.fitfactorymobileworkerapp.data.models.api.request.AddEntryRequest
import com.example.fitfactorymobileworkerapp.data.models.app.StateComplete
import com.example.fitfactorymobileworkerapp.data.rest.RetrofitRepository
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.functional.localStorage.LocalStorage
import com.example.fitfactorymobileworkerapp.presentation.customViews.FrameView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.frame.Frame
import com.otaliastudios.cameraview.frame.FrameProcessor
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class CameraViewViewModel : BaseViewModel(), FrameProcessor {

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    @Inject
    lateinit var localStorage: LocalStorage

    init {
        Injector.component.inject(this)
    }

    private var metaData: FirebaseVisionImageMetadata.Builder? = null
    private var detector: FirebaseVisionBarcodeDetector
    var cameraFacing: Facing = Facing.BACK
    var frameRect: Rect? = null
    val result = MutableLiveData<String>()
    var isAnalyzingEnabled = true

    var checkStatusResult = MutableLiveData<Any>()
    var verifyResult = MutableLiveData<Any>()
    var addEntryResult = MutableLiveData<Any>()

    init {
        metaData = FirebaseVisionImageMetadata.Builder()
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .setRotation(if (cameraFacing == Facing.FRONT) FirebaseVisionImageMetadata.ROTATION_270 else FirebaseVisionImageMetadata.ROTATION_90)

        var options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()

        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
    }

    @WorkerThread
    override fun process(frame: Frame) {

        frameRect?.let {
            val image = metaData?.setWidth(frame.size.width)?.setHeight(frame.size.height)
                ?.setRotation(if (cameraFacing == Facing.FRONT) FirebaseVisionImageMetadata.ROTATION_270 else FirebaseVisionImageMetadata.ROTATION_90)?.let {
                    FirebaseVisionImage.fromByteArray(frame.getData(), it.build())
                } ?: return

            detector.detectInImage(image)
                .addOnSuccessListener { list ->
                    if (list.isNotEmpty() && isAnalyzingEnabled) {
                        list.firstOrNull()?.let {
                            if (checkIfBarCodeIsInFrame(it.boundingBox)) {
                                isAnalyzingEnabled = false
                                result.postValue(it.rawValue)
                            }
                        }
                    }
                }
        }
    }


    fun checkStatus(qrCode: String){
        rxDisposer.add(
            retrofitRepository.checkStatus(qrCode)
                .subscribeBy(
                    onSuccess = {
                        if (it.status){
                            checkStatusResult.postValue(it.data)
                        }
                    },
                    onError = {
                        isAnalyzingEnabled = true
                    }
                )
        )
    }

    fun verify(id: Long){
        rxDisposer.add(
            retrofitRepository.verify(id)
                .subscribeBy(
                    onSuccess = {
                        if (it.status){
                            verifyResult.postValue(StateComplete())
                        }
                    },
                    onError = {
                        Log.e("CameraViewViewModel", it.message ?: "Connection error")
                    }
                )
        )
    }


    fun addEntry(userId: Long){
        rxDisposer.add(
            retrofitRepository.addEntry(AddEntryRequest(userId, localStorage.getUser()?.workPlace?.id ?: return))
                .subscribeBy(
                    onSuccess = {
                        if (it.status){
                            addEntryResult.postValue(StateComplete())
                        }
                    },
                    onError = {
                        Log.e("CameraViewViewModel", it.message ?: "Connection error")
                    }
                )
        )
    }

    private fun checkIfBarCodeIsInFrame(barCodeRect: Rect?): Boolean {
        frameRect?.also { frameRect ->
            barCodeRect?.also { b ->
                if (frameRect.left < b.left && frameRect.top < b.top && frameRect.right > b.right && frameRect.bottom > b.bottom) {
                    val vProp =
                        (b.bottom - b.top).toFloat() / (frameRect.bottom - frameRect.top).toFloat()
                    val hProp =
                        (b.right - b.left).toFloat() / (frameRect.right - frameRect.left).toFloat()

                    if (vProp >= 0.7f && hProp >= 0.7f) {
                        return true
                    }
                }
            }
        }
        return false
    }
}

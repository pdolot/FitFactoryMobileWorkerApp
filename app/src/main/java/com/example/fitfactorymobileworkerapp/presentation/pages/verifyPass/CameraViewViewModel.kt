package com.example.fitfactorymobileworkerapp.presentation.pages.verifyPass

import android.graphics.Rect
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

class CameraViewViewModel : ViewModel(), FrameProcessor {

    private var metaData: FirebaseVisionImageMetadata.Builder? = null
    private var detector: FirebaseVisionBarcodeDetector
    var cameraFacing: Facing = Facing.BACK
    var frameRect: Rect? = null
    val result = MutableLiveData<String>()

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
                .addOnSuccessListener {
                    it.firstOrNull()?.let {
                        if (checkIfBarCodeIsInFrame(it.boundingBox)) {
                            result.postValue(it.rawValue)
                        }
                    }
                }
        }
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

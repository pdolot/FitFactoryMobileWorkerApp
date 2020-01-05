package com.example.fitfactorymobileworkerapp.presentation.pages.verifyPass

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseFragment
import com.example.fitfactorymobileworkerapp.constants.RequestCode
import com.example.fitfactorymobileworkerapp.data.models.api.Pass
import com.example.fitfactorymobileworkerapp.data.models.app.StateComplete
import com.example.fitfactorymobileworkerapp.presentation.customViews.dialogs.VerifyDialog
import com.google.android.material.appbar.AppBarLayout
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Facing
import kotlinx.android.synthetic.main.fragment_camera_view.*
import kotlin.math.abs

class CameraView : BaseFragment() {


    private val viewModel by lazy { CameraViewViewModel() }
    private var scannerMeshHeight = 0f
    private var anim: ViewPropertyAnimator? = null
    private var dialogView: VerifyDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appBarLayout?.setExpanded(false)
        context?.let { dialogView = VerifyDialog(it) }
        if (isCameraPermissionGranted()) {
            startCamera()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), RequestCode.CAMERA_REQUEST_CODE)
        }

        viewModel.result.observe(viewLifecycleOwner, Observer {
            viewModel.checkStatus(it)
        })

        viewModel.checkStatusResult.observe(viewLifecycleOwner, Observer {
            when(it){
                is Pass -> {
                    showDialog(it)
                }
            }
        })

        viewModel.verifyResult.observe(viewLifecycleOwner, Observer {
            when(it){
                is StateComplete -> dialogView?.setStateVerified()
            }
        })

        viewModel.addEntryResult.observe(viewLifecycleOwner, Observer {
            when(it){
                is StateComplete -> findNavController().navigate(CameraViewDirections.toLockerRoom())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        scannerMesh.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                scannerMesh.viewTreeObserver.removeOnGlobalLayoutListener(this)
                scannerMeshHeight = scannerMesh.measuredHeight.toFloat()
                scannerMesh.translationY = scannerMeshHeight
                animateMeshToTop()
                scannerMesh.visibility = View.VISIBLE
                viewModel.frameRect = Rect(
                    frameView.x.toInt(),
                    frameView.y.toInt(),
                    (frameView.x + frameView.measuredWidth).toInt(),
                    (frameView.y + frameView.measuredHeight).toInt()
                )
            }
        })
    }

    private fun showDialog(pass: Pass) {
        dialogView?.apply {
            this.pass = pass
        }
        val dialog = MaterialDialog(activity ?: return).show {
            customView( view = dialogView, scrollable = true)
        }

        dialogView?.onClose = {
            dialog.dismiss()
            viewModel.isAnalyzingEnabled = true
        }
        dialogView?.onVerify = viewModel::verify
        dialogView?.onGiveKey = {
            viewModel.addEntry(it)
            dialog.dismiss()
        }
    }

    private fun startCamera() {
        cameraView.facing = Facing.BACK
        cameraView.addFrameProcessor(viewModel)
        cameraView.setLifecycleOwner(viewLifecycleOwner)
    }

    private fun animateMeshToTop() {
        anim = scannerMesh.animate().apply {
            translationY(-scannerMeshHeight * 2)
            duration = 3000
            interpolator = FastOutSlowInInterpolator()
            withEndAction {
                animateMeshToBottom()
                scannerMesh.rotation = 180f
            }
            start()
        }
    }

    private fun animateMeshToBottom() {
        anim = scannerMesh.animate().apply {
            translationY(scannerMeshHeight)
            duration = 3000
            interpolator = FastOutSlowInInterpolator()
            withEndAction {
                animateMeshToTop()
                scannerMesh.rotation = 360f
            }
            start()
        }

    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission =
            ContextCompat.checkSelfPermission(context ?: return false, Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == RequestCode.CAMERA_REQUEST_CODE) {
            if (isCameraPermissionGranted()) {
                startCamera()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        anim?.cancel()
        super.onDestroyView()
    }

    override var topBarEnabled = true
}

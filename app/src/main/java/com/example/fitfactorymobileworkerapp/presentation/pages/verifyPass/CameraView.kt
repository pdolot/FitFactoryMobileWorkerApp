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
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseFragment
import com.example.fitfactorymobileworkerapp.constants.RequestCode
import com.google.android.material.appbar.AppBarLayout
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Facing
import kotlinx.android.synthetic.main.fragment_camera_view.*
import kotlin.math.abs

class CameraView : BaseFragment() {


    private val viewModel by lazy { CameraViewViewModel() }
    private var scannerMeshHeight = 0f
    private var anim: ViewPropertyAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appBarLayout?.setExpanded(false)

        if (isCameraPermissionGranted()) {
            startCamera()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), RequestCode.CAMERA_REQUEST_CODE)
        }

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

        viewModel.result.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun startCamera() {
        cameraView.facing = Facing.BACK
        cameraView.setLifecycleOwner(viewLifecycleOwner)
        cameraView.addFrameProcessor(viewModel)

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

    override fun onDestroy() {
        super.onDestroy()
        anim?.cancel()
    }
}

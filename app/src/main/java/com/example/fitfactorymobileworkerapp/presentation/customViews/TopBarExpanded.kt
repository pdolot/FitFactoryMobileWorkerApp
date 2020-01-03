package com.example.fitfactorymobileworkerapp.presentation.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.utils.scaleValue
import kotlinx.android.synthetic.main.view_top_bar_expand.view.*
import kotlin.math.abs
import kotlin.math.roundToInt

class TopBarExpanded @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var maxViewSize = 0
        set(value) {
            field = value
            minViewSize = (field / 3f).roundToInt()
            viewSizeDiff = field - minViewSize
        }
    private var minViewSize = 0
    private var viewSizeDiff = 0

    private val startMargin = context.resources.getDimensionPixelSize(R.dimen.profileImageStartMargin)
    private val endMargin = context.resources.getDimensionPixelSize(R.dimen.profileImageEndMargin)
    private val marginDiff = abs(startMargin - endMargin)

    init {
        View.inflate(context, R.layout.view_top_bar_expand, this)
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                maxViewSize = profileImage.measuredHeight
            }

        })
    }

    fun scaleProfileImage(scale: Float){
        val margin = endMargin + (marginDiff * scale).roundToInt()
        val params = profileImage.layoutParams as MarginLayoutParams
        params.height = minViewSize + (viewSizeDiff * scale).roundToInt()
        params.width = minViewSize + (viewSizeDiff * scale).roundToInt()
        params.marginStart = margin
        params.topMargin = margin
        profileImage.layoutParams = params
    }

    fun fadeUserDetails(scale: Float){
        if (scale != 0f){
            userDetails.alpha = scale.scaleValue(0.6f, 1.0f, 0f, 1f)
        }else{
            userDetails.alpha = 0f
        }

    }
}
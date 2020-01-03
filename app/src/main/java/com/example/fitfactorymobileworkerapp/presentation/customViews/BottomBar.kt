package com.example.fitfactorymobileworkerapp.presentation.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fitfactorymobileworkerapp.R
import kotlinx.android.synthetic.main.view_bottombar.view.*

class BottomBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var isFullAlpha = false
    var isAnimated = false

    init {
        View.inflate(context, R.layout.view_bottombar, this)
        bindData()
    }

    fun changeBackgroundRadius(scale: Float){
        roundedBackground.scale = scale
    }

    fun bindData(){
//        progressBar.progress = (it.currentNumberOfMen.toFloat() + it.currentNumberOfWomen.toFloat()) / (it.menLockerRoomLimit.toFloat() + it.womenLockerRoomLimit.toFloat())
        progressBar.progress = 0.4f
        icon.drawable.setTint(progressBar.color)
    }

    fun fade(scale: Float){
        content.alpha = scale
    }

}
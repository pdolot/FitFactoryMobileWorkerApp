package com.example.fitfactorymobileworkerapp.presentation.behaviours

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.fitfactorymobileworkerapp.presentation.customViews.BottomBar
import com.example.fitfactorymobileworkerapp.presentation.customViews.TopBarExpanded

class TopBarExpandedBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<TopBarExpanded>(context, attrs) {

    private var isInitialized = false
    private var startY = 0f

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TopBarExpanded,
        dependency: View
    ): Boolean {
        return dependency is BottomBar
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TopBarExpanded,
        dependency: View
    ): Boolean {

        if (!isInitialized) {
            init(dependency)
        }
        val currentScale = dependency.y / startY

        child.scaleProfileImage(currentScale)
        if (currentScale in 0.6f..1.0f){
            child.fadeUserDetails(currentScale)
        }else{
            child.fadeUserDetails(0f)
        }
        return true
    }

    private fun init(dependency: View) {
        startY = dependency.y
        isInitialized = true
    }
}
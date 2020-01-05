package com.example.fitfactorymobileworkerapp.presentation.activities

import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.fitfactorymobileworkerapp.presentation.customViews.BottomBar
import com.example.fitfactorymobileworkerapp.presentation.customViews.TopBarExpanded
import com.google.android.material.appbar.AppBarLayout


interface MainFragmentInterface {
    fun getAppBarLayout(): AppBarLayout?
    fun getTopBar(): TopBarExpanded?
    fun getBottomBar(): BottomBar?
    fun getCoordinator(): CoordinatorLayout?
    fun getContainer(): FrameLayout?
}
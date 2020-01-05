package com.example.fitfactorymobileworkerapp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.example.fitfactorymobileworkerapp.presentation.activities.MainFragmentInterface
import com.example.fitfactorymobileworkerapp.presentation.behaviours.TopBarExpandedBehavior
import com.example.fitfactorymobileworkerapp.presentation.customViews.BottomBar
import com.example.fitfactorymobileworkerapp.presentation.customViews.TopBarExpanded
import com.google.android.material.appbar.AppBarLayout

abstract class BaseFragment : Fragment() {
    var actions: MainFragmentInterface? = null
    var appBarLayout: AppBarLayout? = null
    var topBar: TopBarExpanded? = null
    var bottomBar: BottomBar? = null
    var frameLayout: FrameLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actions = context as? MainFragmentInterface
        } catch (e: Exception) {
            throw IllegalStateException("Main fragment must implement correct action interface")
        }
        appBarLayout = actions?.getAppBarLayout()
        topBar = actions?.getTopBar()
        bottomBar = actions?.getBottomBar()
        frameLayout = actions?.getContainer()
    }


    override fun onResume() {
        super.onResume()
        if (topBarEnabled && appBarLayout?.visibility != View.VISIBLE) {
            appBarLayout?.visibility = View.VISIBLE
            bottomBar?.visibility = View.VISIBLE
            topBar?.visibility = View.VISIBLE

            val p = topBar?.layoutParams as CoordinatorLayout.LayoutParams
            p.behavior = TopBarExpandedBehavior(context, null)
            topBar?.layoutParams = p

            val c = frameLayout?.layoutParams as CoordinatorLayout.LayoutParams
            c.behavior = AppBarLayout.ScrollingViewBehavior()
            frameLayout?.layoutParams = c

            appBarLayout?.setExpanded(true)

        } else if (!topBarEnabled && appBarLayout?.visibility != View.GONE) {
            val c = frameLayout?.layoutParams as CoordinatorLayout.LayoutParams
            c.behavior = null
            frameLayout?.layoutParams = c

            val p = topBar?.layoutParams as CoordinatorLayout.LayoutParams
            p.behavior = null
            topBar?.layoutParams = p

            appBarLayout?.visibility = View.GONE
            bottomBar?.visibility = View.GONE
            topBar?.visibility = View.GONE
        }
    }

    override fun onDetach() {
        super.onDetach()
        actions = null
        appBarLayout = null
        topBar = null
        bottomBar = null
        frameLayout = null
    }

    abstract var topBarEnabled: Boolean
}
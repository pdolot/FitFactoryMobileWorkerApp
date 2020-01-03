package com.example.fitfactorymobileworkerapp.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.fitfactorymobileworkerapp.presentation.activities.MainFragmentInterface
import com.google.android.material.appbar.AppBarLayout

open class BaseFragment : Fragment() {
    var actions: MainFragmentInterface? = null
    var appBarLayout: AppBarLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actions = context as? MainFragmentInterface
        } catch (e: Exception) {
            throw IllegalStateException("Main fragment must implement correct action interface")
        }
        appBarLayout = actions?.getAppBarLayout()
    }

    override fun onDetach() {
        super.onDetach()
        actions = null
        appBarLayout = null
    }
}
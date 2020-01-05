package com.example.fitfactorymobileworkerapp.presentation.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class Home : BaseFragment() {
    override var topBarEnabled = true

    private val viewModel by lazy { HomeViewModel() }
    private val adapter by lazy { MenuAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        appBarLayout?.setExpanded(true)

        recyclerView.apply {
            layoutManager =  GridLayoutManager(context, 2)
            adapter = this@Home.adapter
        }

        adapter.setData(viewModel.getMenuItems())

    }


}

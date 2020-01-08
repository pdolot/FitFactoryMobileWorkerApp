package com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_fitness_lesson.*

class FitnessLessonsPage : BaseFragment() {
    override var topBarEnabled: Boolean = true

    private val viewModel by lazy { FitnessLessonsViewModel() }
    private val adapter by lazy { FitnessLessonsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fitness_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchFitnessLessons()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FitnessLessonsPage.adapter
        }

        viewModel.fitnessLessons.observe(viewLifecycleOwner, Observer {
            swipe.isRefreshing = false
            adapter.setData(it)
        })

        swipe.setOnRefreshListener {
            viewModel.fetchFitnessLessons()
        }

    }

}

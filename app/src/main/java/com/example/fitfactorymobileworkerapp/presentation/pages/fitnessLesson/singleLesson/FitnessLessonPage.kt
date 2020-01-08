package com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson.singleLesson

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.api.FitnessLesson
import com.example.fitfactorymobileworkerapp.data.models.app.EmptyData
import kotlinx.android.synthetic.main.fragment_fitness_lesson_page.*

class FitnessLessonPage : Fragment() {

    private  val viewModel by lazy {FitnessLessonPageViewModel()}
    private  val adapter by lazy {SignedUpPeopleAdapter()}
    private val args: FitnessLessonPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fitness_lesson_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lessonId = args.id

        viewModel.fitnessLesson.observe(viewLifecycleOwner, Observer {
            it?.let { bindData(it) }
        })

        viewModel.markPresenceState.observe(viewLifecycleOwner, Observer {
            adapter.presentChanged(it.wasPresent, it.position)
        })
    }

    private fun bindData(fitnessLesson: FitnessLesson) {
        fitnessClub_name.text = fitnessLesson.fitnessClub?.name
        Glide.with(context ?: return)
            .load(fitnessLesson.promoImage)
            .placeholder(R.drawable.fitnesslesson_placeholder)
            .centerCrop()
            .into(fitnessLesson_promoImage)

        date.text = fitnessLesson.date
        peopleLimit.text = fitnessLesson.peopleLimit.toString()
        price.text = "${fitnessLesson.price} zł"
        signedUpPeopleCount.text = fitnessLesson.signedUpPeopleCount.toString()
        signedUpPeopleCountProgressBar.progress = (fitnessLesson.signedUpPeopleCount?.toFloat() ?: 0f) / (fitnessLesson.peopleLimit?.toFloat() ?: 1f)
        description.text = fitnessLesson.description

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FitnessLessonPage.adapter
        }

        if (fitnessLesson.signedUpPeople?.size ?: 0 > 0){
            adapter.setData(fitnessLesson.signedUpPeople)
        }else{
            adapter.setData(listOf(EmptyData()))
        }


        adapter.onPresentChange = viewModel::markPresence

    }

}

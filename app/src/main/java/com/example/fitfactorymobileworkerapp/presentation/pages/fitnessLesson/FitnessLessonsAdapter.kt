package com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.api.FitnessLesson
import com.example.fitfactorymobileworkerapp.data.models.app.EmptyData
import kotlinx.android.synthetic.main.item_fitness_lesson.view.*

class FitnessLessonsAdapter : RecyclerView.Adapter<FitnessLessonsAdapter.ViewHolder>() {

    private var items: List<Any>? = null

    fun setData(items: List<Any>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    ViewType.FITNESS_LESSON.ordinal -> {
                        R.layout.item_fitness_lesson
                    }
                    ViewType.EMPTY_DATA.ordinal -> {
                        R.layout.item_empty_data
                    }
                    else -> R.layout.item_empty_data
                }, parent, false
            )
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items?.get(position)) {
            is FitnessLesson -> ViewType.FITNESS_LESSON.ordinal
            is EmptyData -> ViewType.EMPTY_DATA.ordinal
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ViewType.FITNESS_LESSON.ordinal -> {
                val item = items?.get(position) as FitnessLesson?
                holder.itemView.apply {
                    item?.let {
                        fitnessLesson_date.text = it.date
                        fitnessLesson_name.text = it.name
                        Glide.with(context)
                            .load(it.promoImage)
                            .placeholder(R.drawable.fitnesslesson_placeholder)
                            .centerCrop()
                            .into(fitnessLesson_promoImage)

                        moveToDetails.setOnClickListener {
                            findNavController().navigate(FitnessLessonsPageDirections.toFitnessLessonPage(item.id ?: return@setOnClickListener))
                        }
                    }
                }
            }
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    enum class ViewType {
        FITNESS_LESSON,
        EMPTY_DATA
    }
}
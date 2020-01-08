package com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson.singleLesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.api.FitnessLesson
import com.example.fitfactorymobileworkerapp.data.models.api.LessonEntry
import com.example.fitfactorymobileworkerapp.data.models.app.EmptyData
import com.example.fitfactorymobileworkerapp.utils.SpanTextUtil
import kotlinx.android.synthetic.main.item_fitness_lesson.view.*
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_signed_up_person.view.*

class SignedUpPeopleAdapter : RecyclerView.Adapter<SignedUpPeopleAdapter.ViewHolder>() {

    private var items: List<Any>? = null
    var onPresentChange: (id: Long, wasPresent: Boolean, position: Int) -> Unit = { _, _, _ -> }

    fun setData(items: List<Any>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    ViewType.PERSON.ordinal -> {
                        R.layout.item_signed_up_person
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
            is FitnessLesson -> ViewType.PERSON.ordinal
            is EmptyData -> ViewType.EMPTY_DATA.ordinal
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ViewType.PERSON.ordinal -> {
                val item = items?.get(position) as LessonEntry?
                holder.itemView.apply {
                    item?.let {
                        userName.text = "${item.user?.firstName} ${item.user?.lastName}"
                        if (it.isPaid == true){
                            isPaid.text = "Zapłacono: TAK"
                            SpanTextUtil(context)
                                .setSpanOnTextView(isPaid, "TAK", R.color.positiveLight)
                        }else{
                            isPaid.text = "Zapłacono: NIE"
                            SpanTextUtil(context)
                                .setSpanOnTextView(isPaid, "NIE", R.color.negativeLight)
                        }

                        wasPresentCheckbox.isChecked = it.wasPresent ?: false

                        wasPresent.setOnClickListener {
                            onPresentChange(item.id ?: 0, item.wasPresent?.not() ?: false, position)
                        }
                    }
                }
            }
            ViewType.EMPTY_DATA.ordinal -> {
                holder.itemView.apply {
                    title.text = "BRAK ZAPISANYCH OSÓB"
                }
            }
        }


    }

    fun presentChanged(wasPresent: Boolean, position: Int) {
        (items?.get(position) as LessonEntry?)?.wasPresent = wasPresent
        notifyItemChanged(position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    enum class ViewType {
        PERSON,
        EMPTY_DATA
    }
}
package com.example.fitfactorymobileworkerapp.presentation.pages.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.app.MenuItem
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.functional.localStorage.LocalStorage
import kotlinx.android.synthetic.main.item_menu.view.*
import javax.inject.Inject

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    @Inject
    lateinit var localStorage: LocalStorage

    init {
        Injector.component.inject(this)
    }

    private var items: List<MenuItem>? = null

    fun setData(items: List<MenuItem>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)

        holder.itemView.apply {
            title.text = item?.title
            item?.icon?.let { icon.setImageDrawable(ContextCompat.getDrawable(context, it)) }

            setOnClickListener {
                if (item?.title == "WYLOGUJ"){
                    localStorage.setToken(null)
                }
                item?.destinationId?.let {
                    findNavController().navigate(it)
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
package com.example.fitfactorymobileworkerapp.presentation.pages.lockerRoom.lockerRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey
import kotlinx.android.synthetic.main.item_locker_room_key.view.*

class LockerRoomAdapter : RecyclerView.Adapter<LockerRoomAdapter.ViewHolder>() {

    private var items: List<LockerRoomKey>? = null

    fun setData(items: List<LockerRoomKey>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_locker_room_key, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.itemView.apply {
            item?.let {
                keyNumber.text = item.keyNumber.toString()

                if (item.isActive){
                    roundedBackground.bgColor = ContextCompat.getColor(context, item.activeColor)
                }else{
                    roundedBackground.bgColor = ContextCompat.getColor(context, item.defColor)
                }


                setOnClickListener {
                    item.isActive = !item.isActive
                    notifyItemChanged(position)
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
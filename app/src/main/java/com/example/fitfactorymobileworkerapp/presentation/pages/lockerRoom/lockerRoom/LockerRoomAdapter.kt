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

    var takeKeyListener: (Long) -> Unit = {}
    var giveKeyListener: (Long) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_locker_room_key, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.itemView.apply {
            item?.let {
                keyNumber.text = item.number.toString()

                if (!item.free) {
                    if (item.type == "MALE") {
                        roundedBackground.bgColor =
                            ContextCompat.getColor(context, R.color.primaryLight)
                    } else {
                        roundedBackground.bgColor = ContextCompat.getColor(context, R.color.pink)
                    }

                } else {
                    roundedBackground.bgColor =
                        ContextCompat.getColor(context, R.color.primaryBgColor3)
                }


                setOnClickListener {
                    if (!item.free){
                        takeKeyListener(item.id)
                    }else{
                        giveKeyListener(item.id)
                    }
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
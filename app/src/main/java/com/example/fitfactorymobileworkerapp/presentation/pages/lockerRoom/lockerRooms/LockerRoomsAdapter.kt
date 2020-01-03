package com.example.fitfactorymobileworkerapp.presentation.pages.lockerRoom.lockerRooms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseAdapter
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomItem
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomType
import com.example.fitfactorymobileworkerapp.presentation.pages.lockerRoom.lockerRoom.LockerRoomAdapter
import kotlinx.android.synthetic.main.item_locker_room.view.*

class LockerRoomsAdapter : BaseAdapter<LockerRoomsAdapter.ViewHolder>() {

    var currentPosition = 0

    val womenLockerRoomAdapter by lazy { LockerRoomAdapter() }
    val menLockerRoomAdapter by lazy { LockerRoomAdapter() }

    override fun getTitle(position: Int): String? {
        return (items?.get(position) as LockerRoomItem).type?.type
    }

    override fun setCurrentItem(position: Int) {
        currentPosition = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_locker_room, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position) as LockerRoomItem
        holder.itemView.apply {
            item.type?.let {
                when(it){
                    LockerRoomType.MEN -> {
                        recyclerView.apply {
                            layoutManager = GridLayoutManager(context, 4)
                            adapter = this@LockerRoomsAdapter.menLockerRoomAdapter
                        }
                    }
                    LockerRoomType.WOMEN -> {
                        recyclerView.apply {
                            layoutManager = GridLayoutManager(context, 4)
                            adapter = this@LockerRoomsAdapter.womenLockerRoomAdapter
                        }
                    }
                }
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
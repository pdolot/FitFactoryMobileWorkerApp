package com.example.fitfactorymobileworkerapp.presentation.pages.lockerRoom.lockerRooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseFragment
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomItem
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomType
import kotlinx.android.synthetic.main.fragment_locker_room.*

class LockerRooms : BaseFragment() {

    private val viewModel by lazy { LockerRoomsViewModel() }
    private val adapter by lazy { LockerRoomsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locker_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = this@LockerRooms.adapter
        }

        tabLayout.setupWithRecyclerView(recyclerView)

        var menlist = ArrayList<LockerRoomKey>()
        var womenlist = ArrayList<LockerRoomKey>()
        for (i in 0..99){
            menlist.add(LockerRoomKey(i + 1, defColor = R.color.primaryBgColor3, activeColor = R.color.primaryLight))
            womenlist.add(LockerRoomKey(i + 1, defColor = R.color.primaryBgColor3, activeColor = R.color.pink))
        }

        adapter.menLockerRoomAdapter.setData(menlist)
        adapter.womenLockerRoomAdapter.setData(womenlist)

        adapter.setData(listOf(LockerRoomItem(LockerRoomType.WOMEN), LockerRoomItem(LockerRoomType.MEN)))

    }

}

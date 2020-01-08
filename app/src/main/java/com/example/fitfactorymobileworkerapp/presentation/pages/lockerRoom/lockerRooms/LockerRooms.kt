package com.example.fitfactorymobileworkerapp.presentation.pages.lockerRoom.lockerRooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseFragment
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomItem
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomType
import com.google.android.material.snackbar.Snackbar
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

        adapter.setData(listOf(LockerRoomItem(LockerRoomType.WOMEN), LockerRoomItem(LockerRoomType.MEN)))

        viewModel.getLockerRoomByType("MALE").observe(viewLifecycleOwner, Observer {
            adapter.menLockerRoomAdapter.setData(it)
        })

        viewModel.getLockerRoomByType("FEMALE").observe(viewLifecycleOwner, Observer {
            adapter.womenLockerRoomAdapter.setData(it)
        })

        viewModel.result.observe(viewLifecycleOwner, Observer {
            Snackbar.make(recyclerView, it, Snackbar.LENGTH_SHORT).show()
        })

        setListener()

    }

    private fun setListener() {
        adapter.womenLockerRoomAdapter.giveKeyListener = viewModel::giveKey
        adapter.womenLockerRoomAdapter.takeKeyListener = viewModel::takeKey

        adapter.menLockerRoomAdapter.giveKeyListener = viewModel::giveKey
        adapter.menLockerRoomAdapter.takeKeyListener = viewModel::takeKey
    }

    override var topBarEnabled = true
}

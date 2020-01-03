package com.example.fitfactorymobileworkerapp.presentation.pages.home

import androidx.lifecycle.ViewModel
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.app.MenuItem

class HomeViewModel : ViewModel() {

    fun getMenuItems(): List<MenuItem> {

        val verify = MenuItem(title = "WERYFIKUJ", icon = R.drawable.ic_user, destinationId = R.id.cameraView)
        val lockerRoom =
            MenuItem(title = "SZATNIA", icon = R.drawable.ic_user, destinationId = R.id.lockerRoom)
        val logout = MenuItem(title = "WYLOGUJ", icon = R.drawable.ic_user)

        return listOf(
            verify,
            lockerRoom,
            logout
        )
    }
}

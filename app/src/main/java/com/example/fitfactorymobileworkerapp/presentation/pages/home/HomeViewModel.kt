package com.example.fitfactorymobileworkerapp.presentation.pages.home

import androidx.lifecycle.ViewModel
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.app.MenuItem

class HomeViewModel : ViewModel() {

    fun getMenuItems(): List<MenuItem> {

        val fitnessLessons = MenuItem(title = "ZAJĘCIA FITNESS", icon = R.drawable.ic_fitness_lesson, destinationId = R.id.fitnessLessonsPage)
        val verify = MenuItem(title = "WERYFIKUJ", icon = R.drawable.ic_qr, destinationId = R.id.cameraView)
        val lockerRoom =
            MenuItem(title = "SZATNIA", icon = R.drawable.ic_locker_room, destinationId = R.id.lockerRoom)
        val logout = MenuItem(title = "WYLOGUJ", icon = R.drawable.ic_arrow_left, destinationId = R.id.toSignIn)

        return listOf(
            fitnessLessons,
            verify,
            lockerRoom,
            logout
        )
    }
}

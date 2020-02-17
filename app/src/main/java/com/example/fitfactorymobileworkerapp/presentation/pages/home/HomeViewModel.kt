package com.example.fitfactorymobileworkerapp.presentation.pages.home

import androidx.lifecycle.ViewModel
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.app.MenuItem
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.functional.localStorage.LocalStorage
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var localStorage: LocalStorage

    init {
        Injector.component.inject(this)
    }

    fun getMenuItems(): List<MenuItem> {

        val fitnessLessons = MenuItem(
            title = "ZAJĘCIA FITNESS",
            icon = R.drawable.ic_fitness_lesson,
            destinationId = R.id.fitnessLessonsPage
        )
        val verify =
            MenuItem(title = "WERYFIKUJ", icon = R.drawable.ic_qr, destinationId = R.id.cameraView)
        val lockerRoom =
            MenuItem(
                title = "SZATNIA",
                icon = R.drawable.ic_locker_room,
                destinationId = R.id.lockerRoom
            )
        val logout = MenuItem(
            title = "WYLOGUJ",
            icon = R.drawable.ic_arrow_left,
            destinationId = R.id.toSignIn
        )

        localStorage.getUser()?.roles?.let {
            if (it.contains("ROLE_COACH")) {
                return listOf(
                    fitnessLessons,
                    verify,
                    lockerRoom,
                    logout
                )
            } else {
                listOf(
                    verify,
                    lockerRoom,
                    logout
                )
            }
        }

        return emptyList()
    }
}

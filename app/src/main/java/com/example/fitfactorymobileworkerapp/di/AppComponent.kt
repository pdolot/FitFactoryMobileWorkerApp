package com.example.fitfactorymobileworkerapp.di

import com.example.fitfactorymobileworkerapp.data.rest.TokenInterceptor
import com.example.fitfactorymobileworkerapp.di.modules.AppModule
import com.example.fitfactorymobileworkerapp.di.modules.DbModule
import com.example.fitfactorymobileworkerapp.di.modules.RestModule
import com.example.fitfactorymobileworkerapp.presentation.activities.MainActivity
import com.example.fitfactorymobileworkerapp.presentation.activities.MainViewModel
import com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson.FitnessLessonsViewModel
import com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson.singleLesson.FitnessLessonPageViewModel
import com.example.fitfactorymobileworkerapp.presentation.pages.home.HomeViewModel
import com.example.fitfactorymobileworkerapp.presentation.pages.home.MenuAdapter
import com.example.fitfactorymobileworkerapp.presentation.pages.lockerRoom.lockerRooms.LockerRoomsViewModel
import com.example.fitfactorymobileworkerapp.presentation.pages.signIn.SignInViewModel
import com.example.fitfactorymobileworkerapp.presentation.pages.verifyPass.CameraViewViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DbModule::class,
        RestModule::class
    ]
)
interface AppComponent {
    // Activities
    fun inject(into: MainActivity)

    fun inject(into: TokenInterceptor)
    fun inject(into: SignInViewModel)
    fun inject(into: MainViewModel)
    fun inject(into: MenuAdapter)
    fun inject(into: LockerRoomsViewModel)
    fun inject(into: CameraViewViewModel)
    fun inject(into: FitnessLessonsViewModel)
    fun inject(into: FitnessLessonPageViewModel)
    fun inject(into: HomeViewModel)


}
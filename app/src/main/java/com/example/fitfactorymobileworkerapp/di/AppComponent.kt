package com.example.fitfactorymobileworkerapp.di

import com.example.fitfactorymobileworkerapp.data.rest.TokenInterceptor
import com.example.fitfactorymobileworkerapp.di.modules.AppModule
import com.example.fitfactorymobileworkerapp.di.modules.DbModule
import com.example.fitfactorymobileworkerapp.di.modules.RestModule
import com.example.fitfactorymobileworkerapp.presentation.activities.MainActivity
import com.example.fitfactorymobileworkerapp.presentation.pages.signIn.SignInViewModel
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


}
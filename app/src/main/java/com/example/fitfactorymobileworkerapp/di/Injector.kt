package com.example.fitfactorymobileworkerapp.di

import com.example.fitfactorymobileworkerapp.app.App
import com.example.fitfactorymobileworkerapp.di.modules.AppModule
import com.example.fitfactorymobileworkerapp.di.modules.DbModule
import com.example.fitfactorymobileworkerapp.di.modules.RestModule

object Injector {
    lateinit var component: AppComponent

    fun init(application: App, baseUrl: String) {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .restModule(RestModule(baseUrl))
            .dbModule(DbModule(application))
            .build()
    }
}
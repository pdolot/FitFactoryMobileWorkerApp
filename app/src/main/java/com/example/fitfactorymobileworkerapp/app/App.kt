package com.example.fitfactorymobileworkerapp.app

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfactorymobileworkerapp.di.Injector

class App : Application() {
    lateinit var activity: AppCompatActivity

    override fun onCreate() {
        super.onCreate()
    }

    fun setCurrentActivity(activity: AppCompatActivity) {
        this.activity = activity
    }
}
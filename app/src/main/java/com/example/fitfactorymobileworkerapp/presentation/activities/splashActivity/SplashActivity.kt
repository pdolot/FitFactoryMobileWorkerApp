package com.example.fitfactorymobileworkerapp.presentation.activities.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.app.App
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.presentation.activities.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val db = FirebaseDatabase.getInstance()
        db.reference.child("server").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                val baseUrl = "http://192.168.0.1:8080/"
                Injector.init(application as App, baseUrl)
                startMainActivity()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val baseUrl = "http://" + (dataSnapshot.value as HashMap<*, *>)["host"].toString() + ":8080/"
                Injector.init(application as App, baseUrl)
                startMainActivity()
            }
        })
    }


    private fun startMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        mainActivity.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        mainActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        mainActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        mainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainActivity)
    }
}

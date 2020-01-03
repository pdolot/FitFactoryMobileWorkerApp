package com.example.fitfactorymobileworkerapp.functional.localStorage

import android.content.Context
import com.example.fitfactorymobileworkerapp.data.models.app.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefLocalStorage(context: Context) : LocalStorage {
    private var sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val liveSharedPreferences = LiveSharedPreferences(sharedPref)


    override fun isLoggedLive(): LivePreference<Boolean> {
        return liveSharedPreferences.getBoolean(IS_LOGGED, false)
    }

    override fun isLogged(): Boolean {
        return sharedPref.getBoolean(IS_LOGGED, false)
    }

    override fun readToken(): String? {
        return sharedPref.getString(TOKEN, null)
    }

    override fun setToken(token: String?) {
        sharedPref.edit().apply {
            putBoolean(IS_LOGGED, token != null)
            putString(TOKEN, token)
            apply()
        }
    }

    override fun setUser(user: User) {
        sharedPref.edit().apply {
            putString(USER, Gson().toJson(user))
            apply()
        }
    }

    override fun getUser(): User? {
        val user = sharedPref.getString(USER, null) ?: return null
        val type = object : TypeToken<User>() {}.type
        return Gson().fromJson(user, type)
    }


    
    companion object {
        const val PREFS_NAME = "fitfactory_workerapp"
        const val IS_LOGGED = "isLoggedLive"
        const val TOKEN = "token"
        const val USER = "user"
    }

}
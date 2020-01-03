package com.example.fitfactorymobileworkerapp.functional.localStorage

import com.example.fitfactorymobileworkerapp.data.models.app.User

interface LocalStorage {
    fun isLoggedLive(): LivePreference<Boolean>
    fun isLogged(): Boolean
    fun setToken(token: String?)
    fun readToken(): String?
    fun setUser(user: User)
    fun getUser(): User?
}
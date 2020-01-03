package com.example.fitfactorymobileworkerapp.data.rest

import com.example.fitfactorymobileworkerapp.constants.Api.BEARER
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.functional.localStorage.LocalStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor : Interceptor {
    @Inject
    lateinit var localStorage: LocalStorage

    init {
        Injector.component.inject(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        if (localStorage.isLogged()) {
            localStorage.readToken()?.let {
                newRequest.addHeader("Authorization", "$BEARER $it")
            }
        }
        return chain.proceed(newRequest.build())
    }
}
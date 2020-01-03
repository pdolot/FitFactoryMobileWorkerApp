package com.example.fitfactorymobileworkerapp.data.rest

import com.example.fitfactorymobileworkerapp.data.models.api.request.SignInRequest
import com.example.fitfactorymobileworkerapp.data.models.api.response.SignInResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("user/signIn")
    fun signIn(@Body signInRequest: SignInRequest): Single<SignInResponse>
}
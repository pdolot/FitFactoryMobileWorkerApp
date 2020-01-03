package com.example.fitfactorymobileworkerapp.data.rest

import com.example.fitfactorymobileworkerapp.data.models.api.request.SignInRequest
import com.example.fitfactorymobileworkerapp.data.models.api.response.SignInResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitService: RetrofitService) {

    fun signIn(signInRequest: SignInRequest): Single<SignInResponse>{
        return retrofitService.signIn(signInRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
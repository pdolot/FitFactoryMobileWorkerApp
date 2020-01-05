package com.example.fitfactorymobileworkerapp.data.rest

import com.example.fitfactorymobileworkerapp.data.models.api.BaseResponse
import com.example.fitfactorymobileworkerapp.data.models.api.request.AddEntryRequest
import com.example.fitfactorymobileworkerapp.data.models.api.request.SignInRequest
import com.example.fitfactorymobileworkerapp.data.models.api.response.LockerRoomKeysResponse
import com.example.fitfactorymobileworkerapp.data.models.api.response.PassResponse
import com.example.fitfactorymobileworkerapp.data.models.api.response.SignInResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @POST("user/signIn")
    fun signIn(@Body signInRequest: SignInRequest): Single<SignInResponse>

    @GET("/fitnessClub/getFitnessClubKeys")
    fun getFitnessClubLockerRoomKeys(@Query("id") id: Long): Observable<LockerRoomKeysResponse>

    @POST("/fitnessClub/giveLockerRoomKey")
    fun giveLockerRoomKey(@Query("id") id: Long): Single<BaseResponse>

    @POST("/fitnessClub/takeLockerRoomKey")
    fun takeLockerRoomKey(@Query("id") id: Long): Single<BaseResponse>

    @GET("/pass/checkStatus")
    fun checkPassStatus(@Query("qrCode") qrCode: String): Single<PassResponse>

    @POST("pass/verify")
    fun verify(@Query("id") id: Long): Single<BaseResponse>

    @POST("/entry/add")
    fun addEntry(@Body entry: AddEntryRequest): Single<BaseResponse>
}
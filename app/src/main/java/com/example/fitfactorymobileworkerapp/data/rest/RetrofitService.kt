package com.example.fitfactorymobileworkerapp.data.rest

import com.example.fitfactorymobileworkerapp.data.models.api.BaseResponse
import com.example.fitfactorymobileworkerapp.data.models.api.request.AddEntryRequest
import com.example.fitfactorymobileworkerapp.data.models.api.request.SignInRequest
import com.example.fitfactorymobileworkerapp.data.models.api.response.*
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

    @GET("/coach/getCoachLessons")
    fun getFitnessLessons(@Query("id") id: Long): Single<FitnessLessonsResponse>

    @GET("fitnessLesson/getFitnessLesson")
    fun getFitnessLesson(@Query("id") id: Long): Single<FitnessLessonResponse>

    @POST("lessonEntry/markPresence")
    fun markPresence(@Query("id") id:Long, @Query("present") present: Boolean): Single<BaseResponse>


}
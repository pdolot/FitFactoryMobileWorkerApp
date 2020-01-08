package com.example.fitfactorymobileworkerapp.data.rest

import com.example.fitfactorymobileworkerapp.data.models.api.BaseResponse
import com.example.fitfactorymobileworkerapp.data.models.api.request.AddEntryRequest
import com.example.fitfactorymobileworkerapp.data.models.api.request.SignInRequest
import com.example.fitfactorymobileworkerapp.data.models.api.response.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitService: RetrofitService) {

    fun signIn(signInRequest: SignInRequest): Single<SignInResponse>{
        return retrofitService.signIn(signInRequest)
            .delaySubscription(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFitnessClubLockerRoomKeys(id: Long): Observable<LockerRoomKeysResponse>{
        return retrofitService.getFitnessClubLockerRoomKeys(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun giveLockerRoomKey(id: Long): Single<BaseResponse>{
        return retrofitService.giveLockerRoomKey(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun takeLockerRoomKey(id: Long): Single<BaseResponse>{
        return retrofitService.takeLockerRoomKey(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun checkStatus(qrCode: String): Single<PassResponse>{
        return retrofitService.checkPassStatus(qrCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun verify(id: Long): Single<BaseResponse>{
        return retrofitService.verify(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addEntry(entry: AddEntryRequest): Single<BaseResponse>{
        return retrofitService.addEntry(entry)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFitnessLessons(id: Long): Single<FitnessLessonsResponse>{
        return retrofitService.getFitnessLessons(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFitnessLesson(id: Long): Single<FitnessLessonResponse>{
        return retrofitService.getFitnessLesson(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun markPresence(id: Long, present: Boolean): Single<BaseResponse>{
        return retrofitService.markPresence(id, present)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
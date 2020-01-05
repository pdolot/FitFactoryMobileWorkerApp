package com.example.fitfactorymobileworkerapp.presentation.activities

import androidx.lifecycle.viewModelScope
import com.example.fitfactorymobileworkerapp.base.BaseViewModel
import com.example.fitfactorymobileworkerapp.data.database.lockerRoomKey.LockerRoomRepository
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey
import com.example.fitfactorymobileworkerapp.data.rest.RetrofitRepository
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel : BaseViewModel() {
    @Inject
    lateinit var localStorage: LocalStorage

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    @Inject
    lateinit var lockerRoomRepository: LockerRoomRepository

    init {
        Injector.component.inject(this)
    }


    fun fetchLockerRoom(){
        rxDisposer.add(retrofitRepository.getFitnessClubLockerRoomKeys(localStorage.getUser()?.workPlace?.id ?: 0)
            .repeatWhen {s ->
                s.delay(5, TimeUnit.SECONDS)
            }
            .subscribeBy(
                onNext = {
                    if (it.status){
//                        deleteAll()
                        insert(it.data)
                    }
                },
                onError = {
                    fetchLockerRoom()
                }
            ))
    }

    private fun insert(keys: List<LockerRoomKey>?) = viewModelScope.launch {
        lockerRoomRepository.insert(keys)
    }

    fun getLockerRoomKeys() = lockerRoomRepository.getAll()

    fun deleteAll() = viewModelScope.launch {
        lockerRoomRepository.deleteAll()
    }
}
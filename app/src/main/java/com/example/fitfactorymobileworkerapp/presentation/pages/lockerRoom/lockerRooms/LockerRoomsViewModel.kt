package com.example.fitfactorymobileworkerapp.presentation.pages.lockerRoom.lockerRooms

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfactorymobileworkerapp.base.BaseViewModel
import com.example.fitfactorymobileworkerapp.data.database.lockerRoomKey.LockerRoomRepository
import com.example.fitfactorymobileworkerapp.data.rest.RetrofitRepository
import com.example.fitfactorymobileworkerapp.di.Injector
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LockerRoomsViewModel : BaseViewModel() {
    @Inject
    lateinit var lockerRoomRepository: LockerRoomRepository

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    init {
        Injector.component.inject(this)
    }

    val result = MutableLiveData<String>()

    fun getLockerRoomByType(type: String) = lockerRoomRepository.getByType(type)

    fun giveKey(id: Long) {
        rxDisposer.add(retrofitRepository.giveLockerRoomKey(id)
            .subscribeBy(
                onSuccess = {
                    if (it.status){
                        result.postValue("Klucz został wydany")
                        Log.i("LockerRoomsViewModel", it.message)
                    }else{
                        result.postValue("Klucz został już wydany wcześniej")
                        Log.e("LockerRoomsViewModel", it.message)
                    }

                },
                onError = {
                    result.postValue("Błąd połączenia")
                    Log.e("LockerRoomsViewModel", it.message)
                }
            ))
    }

    fun takeKey(id: Long) {
        rxDisposer.add(retrofitRepository.takeLockerRoomKey(id)
            .subscribeBy(
                onSuccess = {
                    if (it.status){
                        result.postValue("Klucz został odebrany")
                        Log.i("LockerRoomsViewModel", it.message)
                    }else{
                        result.postValue("Klucz został już odebrany wcześniej")
                        Log.e("LockerRoomsViewModel", it.message)
                    }

                },
                onError = {
                    result.postValue("Błąd połączenia")
                    Log.e("LockerRoomsViewModel", it.message)
                }
            ))
    }
}

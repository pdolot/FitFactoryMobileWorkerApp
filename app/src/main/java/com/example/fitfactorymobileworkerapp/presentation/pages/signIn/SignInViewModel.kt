package com.example.fitfactorymobileworkerapp.presentation.pages.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfactorymobileworkerapp.base.BaseViewModel
import com.example.fitfactorymobileworkerapp.data.models.api.request.SignInRequest
import com.example.fitfactorymobileworkerapp.data.models.app.StateComplete
import com.example.fitfactorymobileworkerapp.data.models.app.StateError
import com.example.fitfactorymobileworkerapp.data.models.app.StateInProgress
import com.example.fitfactorymobileworkerapp.data.rest.RetrofitRepository
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SignInViewModel : BaseViewModel() {

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    @Inject
    lateinit var localStorage: LocalStorage

    init {
        Injector.component.inject(this)
    }

    val stateStatus = MutableLiveData<Any>()

    fun signIn(username: String, password: String){
        stateStatus.postValue(StateInProgress())
        rxDisposer.add(retrofitRepository.signIn(SignInRequest(username, password))
            .subscribeBy(
                onSuccess = {
                    localStorage.setToken(it.token)
                    localStorage.setUser(it.user)
                    stateStatus.postValue(StateComplete())
                },
                onError = {
                    stateStatus.postValue(StateError(message = it.message))
                }
            ))
    }
}

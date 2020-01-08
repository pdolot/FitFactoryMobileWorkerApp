package com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson

import androidx.lifecycle.MutableLiveData
import com.example.fitfactorymobileworkerapp.base.BaseViewModel
import com.example.fitfactorymobileworkerapp.data.models.app.EmptyData
import com.example.fitfactorymobileworkerapp.data.rest.RetrofitRepository
import com.example.fitfactorymobileworkerapp.di.Injector
import com.example.fitfactorymobileworkerapp.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FitnessLessonsViewModel : BaseViewModel() {

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    @Inject
    lateinit var localStorage: LocalStorage

    val fitnessLessons = MutableLiveData<List<Any>>()

    init {
        Injector.component.inject(this)
        fitnessLessons.postValue(listOf(EmptyData()))
    }

    fun fetchFitnessLessons() {
        rxDisposer.add(retrofitRepository.getFitnessLessons(localStorage.getUser()?.id ?: return)
            .subscribeBy(
                onSuccess = {
                    if (it.status) {
                        fitnessLessons.postValue(it.data)
                    } else {
                        fitnessLessons.postValue(listOf(EmptyData()))
                    }
                },
                onError = {
                    fitnessLessons.postValue(listOf(EmptyData()))
                }
            ))
    }
}

package com.example.fitfactorymobileworkerapp.presentation.pages.fitnessLesson.singleLesson

import androidx.lifecycle.MutableLiveData
import com.example.fitfactorymobileworkerapp.base.BaseViewModel
import com.example.fitfactorymobileworkerapp.data.models.api.FitnessLesson
import com.example.fitfactorymobileworkerapp.data.rest.RetrofitRepository
import com.example.fitfactorymobileworkerapp.di.Injector
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FitnessLessonPageViewModel : BaseViewModel() {

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    init {
        Injector.component.inject(this)
    }

    var lessonId: Long? = null
        set(value) {
            field = value
            fetchFitnessLesson()
        }

    val fitnessLesson = MutableLiveData<FitnessLesson?>()
    val markPresenceState = MutableLiveData<MarkPresenceState>()

    private fun fetchFitnessLesson(){
        rxDisposer.add(retrofitRepository.getFitnessLesson(lessonId ?: return).subscribeBy(
            onSuccess = {
                if (it.status){
                    fitnessLesson.postValue(it.data)
                }
            },
            onError = {

            }
        ))
    }

    fun markPresence(id: Long, wasPresent: Boolean, position: Int){
        rxDisposer.add(retrofitRepository.markPresence(id, wasPresent)
            .subscribeBy(
                onSuccess = {
                    if (it.status){
                        markPresenceState.postValue(MarkPresenceState(wasPresent, position))
                    }else{
                        markPresenceState.postValue(MarkPresenceState(!wasPresent, position))
                    }
                },
                onError = {
                    markPresenceState.postValue(MarkPresenceState(!wasPresent, position))
                }
            ))
    }

    class MarkPresenceState(var wasPresent: Boolean, var position: Int)
}

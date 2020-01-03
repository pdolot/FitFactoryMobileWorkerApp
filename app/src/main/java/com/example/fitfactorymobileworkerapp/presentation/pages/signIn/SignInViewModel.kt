package com.example.fitfactorymobileworkerapp.presentation.pages.signIn

import androidx.lifecycle.ViewModel
import com.example.fitfactorymobileworkerapp.data.rest.RetrofitRepository
import com.example.fitfactorymobileworkerapp.di.Injector
import javax.inject.Inject

class SignInViewModel : ViewModel() {

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    init {
        Injector.component.inject(this)
    }
}

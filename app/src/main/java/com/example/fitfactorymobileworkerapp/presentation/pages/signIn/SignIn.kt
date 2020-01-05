package com.example.fitfactorymobileworkerapp.presentation.pages.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.base.BaseFragment
import com.example.fitfactorymobileworkerapp.data.models.app.StateComplete
import com.example.fitfactorymobileworkerapp.data.models.app.StateError
import com.example.fitfactorymobileworkerapp.data.models.app.StateInProgress
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignIn : BaseFragment() {
    private val viewModel by lazy { SignInViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                is StateInProgress -> {
                    signIn.startAnim()
                }
                is StateComplete -> {
                    signIn.onSuccess("Zalogowano")
                    signIn.stop()
                }
                is StateError -> {
                    signIn.onError(it.message ?: "Błąd połączenia")
                    signIn.stop()
                }
            }
        })
        signIn.setOnClickListener {
            if (!userName.text.toString().isBlank() && !userPassword.text.toString().isBlank()){
                viewModel.signIn(userName.text.toString().trim(), userPassword.text.toString().trim())
            }
        }

        signIn.onActionEnd = ::navigateToHome
    }

    private fun navigateToHome(status: Boolean){
        if (status){
            findNavController().navigate(R.id.toHomePage)
        }
    }

    override var topBarEnabled = false

}

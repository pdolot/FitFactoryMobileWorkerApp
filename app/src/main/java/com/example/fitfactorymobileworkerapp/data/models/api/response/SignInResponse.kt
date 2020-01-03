package com.example.fitfactorymobileworkerapp.data.models.api.response

import com.example.fitfactorymobileworkerapp.data.models.app.User

data class SignInResponse(
    val token: String,
    val user: User
)
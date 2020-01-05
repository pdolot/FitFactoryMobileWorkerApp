package com.example.fitfactorymobileworkerapp.data.models.api

import com.example.fitfactorymobileworkerapp.data.models.app.User

data class Pass(
    val passId: Long? = null,
    val passUser: User? = null,
    val active: Boolean? = null,
    val verified: Boolean? = null
)
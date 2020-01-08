package com.example.fitfactorymobileworkerapp.data.models.api

import com.example.fitfactorymobileworkerapp.data.models.app.User

data class LessonEntry(
    val id: Long? = null,
    val user: User? = null,
    val isPaid: Boolean? = null,
    var wasPresent: Boolean? = null
)

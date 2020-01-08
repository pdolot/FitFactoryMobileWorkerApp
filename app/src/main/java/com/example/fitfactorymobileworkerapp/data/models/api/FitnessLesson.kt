package com.example.fitfactorymobileworkerapp.data.models.api

import com.example.fitfactorymobileworkerapp.data.models.app.User
import com.example.fitfactorymobileworkerapp.data.models.app.WorkPlace

data class FitnessLesson(
    val id: Long? = null,
    val name: String? = null,
    val coach: User? = null,
    val fitnessClub: WorkPlace? = null,
    val peopleLimit: Int? = null,
    val date: String,
    val signedUpPeople: List<LessonEntry>? = null,
    val signedUpPeopleCount: Int? = null,
    val price: Double? = null,
    val description: String? = null,
    val isActive: Boolean? = null,
    val promoImage: String? = null
)
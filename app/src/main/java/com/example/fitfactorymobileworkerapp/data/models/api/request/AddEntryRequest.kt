package com.example.fitfactorymobileworkerapp.data.models.api.request

data class AddEntryRequest(
    var userId: Long? = null,
    var fitnessClubId: Long? = null
)
package com.example.fitfactorymobileworkerapp.data.models.api.response

import com.example.fitfactorymobileworkerapp.data.models.api.BaseResponse
import com.example.fitfactorymobileworkerapp.data.models.api.FitnessLesson

data class FitnessLessonResponse(
    val data: FitnessLesson? = null
) : BaseResponse()

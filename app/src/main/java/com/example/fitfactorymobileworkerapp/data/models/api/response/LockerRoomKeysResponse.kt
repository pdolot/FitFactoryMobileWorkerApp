package com.example.fitfactorymobileworkerapp.data.models.api.response

import com.example.fitfactorymobileworkerapp.data.models.api.BaseResponse
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey

data class LockerRoomKeysResponse(
    val data: List<LockerRoomKey>? = null
) : BaseResponse()
package com.example.fitfactorymobileworkerapp.data.models.app

data class LockerRoomItem(
    val type: LockerRoomType? = null
)

data class LockerRoomKey(
    val keyNumber: Int? = null,
    val defColor: Int,
    val activeColor: Int,
    var isActive: Boolean = false
)

enum class LockerRoomType(val type: String){
    WOMEN("SZATNIA DAMSKA"),
    MEN("SZATNIA MĘSKA")
}
package com.example.fitfactorymobileworkerapp.data.models.app

import androidx.room.Entity
import androidx.room.PrimaryKey

data class LockerRoomItem(
    val type: LockerRoomType? = null
)

@Entity
data class LockerRoomKey(
    @PrimaryKey(autoGenerate = false)
    val keyNumber: Int? = null,
    val defColor: Int,
    val activeColor: Int,
    var isActive: Boolean = false
)

enum class LockerRoomType(val type: String){
    WOMEN("SZATNIA DAMSKA"),
    MEN("SZATNIA MĘSKA")
}
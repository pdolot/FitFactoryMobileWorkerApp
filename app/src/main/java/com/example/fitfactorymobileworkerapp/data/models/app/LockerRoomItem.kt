package com.example.fitfactorymobileworkerapp.data.models.app

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class LockerRoomItem(
    val type: LockerRoomType? = null
)

@Entity
data class LockerRoomKey(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val number: Int? = null,
    var free: Boolean = true,
    val type: String? = null
)

enum class LockerRoomType(val type: String){
    WOMEN("SZATNIA DAMSKA"),
    MEN("SZATNIA MĘSKA")
}
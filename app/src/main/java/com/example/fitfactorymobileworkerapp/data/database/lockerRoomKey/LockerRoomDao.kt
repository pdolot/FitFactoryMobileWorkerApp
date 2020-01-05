package com.example.fitfactorymobileworkerapp.data.database.lockerRoomKey

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey

@Dao
interface LockerRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: List<LockerRoomKey>?)

    @Query("SELECT * from LockerRoomKey")
    fun getAll(): LiveData<List<LockerRoomKey>>

    @Query("SELECT * from LockerRoomKey WHERE type= :type")
    fun getByType(type: String): LiveData<List<LockerRoomKey>>

    @Query("SELECT * from LockerRoomKey WHERE id= :id")
    fun getById(id: Long): LockerRoomKey

    @Query("DELETE FROM LockerRoomKey")
    suspend fun deleteAll()
}
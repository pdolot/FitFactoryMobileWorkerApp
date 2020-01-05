package com.example.fitfactorymobileworkerapp.data.database.lockerRoomKey

import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey

class LockerRoomRepository(private val lockerRoomDao: LockerRoomDao) {

    suspend fun insert(exercises: List<LockerRoomKey>?) = lockerRoomDao.insert(exercises)

    fun getAll() = lockerRoomDao.getAll()

    fun getByType(type: String) = lockerRoomDao.getByType(type)

    suspend fun deleteAll() = lockerRoomDao.deleteAll()
}
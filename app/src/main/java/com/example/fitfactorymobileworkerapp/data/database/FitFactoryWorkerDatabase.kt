package com.example.fitfactorymobileworkerapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitfactorymobileworkerapp.data.models.app.LockerRoomKey

@Database(
    entities = [LockerRoomKey::class], version = 1
)
@TypeConverters( Converters::class)
abstract class FitFactoryWorkerDatabase : RoomDatabase(){

}
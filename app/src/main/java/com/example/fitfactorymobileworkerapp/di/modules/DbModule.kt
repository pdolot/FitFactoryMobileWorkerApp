package com.example.fitfactorymobileworkerapp.di.modules

import androidx.room.Room
import com.example.fitfactorymobileworkerapp.app.App
import com.example.fitfactorymobileworkerapp.data.database.FitFactoryWorkerDatabase
import com.example.fitfactorymobileworkerapp.data.database.lockerRoomKey.LockerRoomDao
import com.example.fitfactorymobileworkerapp.data.database.lockerRoomKey.LockerRoomRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(private val application: App) {
    @Singleton
    @Provides
    fun provideRoomDatabase(): FitFactoryWorkerDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            FitFactoryWorkerDatabase::class.java,
            "FitFactoryWorkerDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLockerRoomDao(database: FitFactoryWorkerDatabase): LockerRoomDao{
        return database.lockerRoomDao()
    }

    @Singleton
    @Provides
    fun provideLockerRoomRepository(lockerRoomDao: LockerRoomDao): LockerRoomRepository{
        return LockerRoomRepository(lockerRoomDao)
    }
}
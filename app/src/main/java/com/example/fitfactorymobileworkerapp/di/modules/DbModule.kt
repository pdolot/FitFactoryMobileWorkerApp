package com.example.fitfactorymobileworkerapp.di.modules

import androidx.room.Room
import com.example.fitfactorymobileworkerapp.app.App
import com.example.fitfactorymobileworkerapp.data.database.FitFactoryWorkerDatabase
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

//    @Singleton
//    @Provides
//    fun provideFitnessClubDao(database: FitFactoryWorkerDatabase): FitnessClubDao{
//        return database.fitnessClubDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideFitnessClubRepository(fitnessClubDao: FitnessClubDao): FitnessClubRepository{
//        return FitnessClubRepository(fitnessClubDao)
//    }
}
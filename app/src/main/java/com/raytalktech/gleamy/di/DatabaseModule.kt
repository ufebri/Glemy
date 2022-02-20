package com.raytalktech.gleamy.di

import android.content.Context
import androidx.room.Room
import com.raytalktech.gleamy.data.local.AppDao
import com.raytalktech.gleamy.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.appDao()
    }
}
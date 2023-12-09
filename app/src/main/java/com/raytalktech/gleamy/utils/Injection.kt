package com.raytalktech.gleamy.utils

import android.content.Context
import com.raytalktech.gleamy.data.source.DataRepository
import com.raytalktech.gleamy.data.source.local.LocalDataSource
import com.raytalktech.gleamy.data.source.local.room.AppDatabase
import com.raytalktech.gleamy.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val database = AppDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.appDao())
        val appExecutors = AppExecutors()
        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
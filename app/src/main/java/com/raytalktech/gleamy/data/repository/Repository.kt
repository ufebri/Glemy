package com.raytalktech.gleamy.data.repository

import com.raytalktech.gleamy.data.local.AppDao
import com.raytalktech.gleamy.data.remote.AppRemoteDataSource
import com.raytalktech.gleamy.model.DataResponse
import com.raytalktech.gleamy.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(
    private val appRemoteDataSource: AppRemoteDataSource,
    var appDao: AppDao
) {

    suspend fun fetchOneCallWeather(lat: String, lon: String): Flow<Result<DataResponse?>> {
        return flow {
            emit(Result.loading())
            val result = appRemoteDataSource.fetchOneCallWeather(lat = lat, lon = lon)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
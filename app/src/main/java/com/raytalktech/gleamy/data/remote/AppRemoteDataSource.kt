package com.raytalktech.gleamy.data.remote

import com.raytalktech.gleamy.Utils.ErrorUtils
import com.raytalktech.gleamy.model.DataResponse
import com.raytalktech.gleamy.network.AppApiService
import retrofit2.Response
import retrofit2.Retrofit
import com.raytalktech.gleamy.model.Result
import javax.inject.Inject

class AppRemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    suspend fun fetchOneCallWeather(lat: String, lon: String): Result<DataResponse> {
        val appApiService = retrofit.create(AppApiService::class.java)
        return getResponse(
            request = { appApiService.getOneCallWeather(lat = lat, lon = lon) },
            defaultErrorMessage = "Error Fetching Response"
        )
    }


    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}
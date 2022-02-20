package com.raytalktech.gleamy.network

import com.raytalktech.gleamy.Utils.Constants
import com.raytalktech.gleamy.model.DataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApiService {

    @GET("/data/2.5/onecall")
    fun getOneCallWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely,hourly",
        @Query("appid") appID: String = Constants.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<DataResponse>
}
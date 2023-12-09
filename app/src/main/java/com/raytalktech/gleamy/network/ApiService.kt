package com.raytalktech.gleamy.network

import com.raytalktech.gleamy.BuildConfig
import com.raytalktech.gleamy.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/onecall")
    fun getOneCallWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely,hourly",
        @Query("appid") appID: String = BuildConfig.APIKEY,
        @Query("units") units: String = "metric"
    ): Call<DataResponse>
}
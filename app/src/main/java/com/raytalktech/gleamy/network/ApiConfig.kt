package com.raytalktech.gleamy.network

import com.raytalktech.gleamy.BuildConfig
import com.raytalktech.gleamy.Utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor()

            if (BuildConfig.BUILD_TYPE == "debug")
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
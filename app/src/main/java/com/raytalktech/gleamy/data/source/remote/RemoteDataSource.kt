package com.raytalktech.gleamy.data.source.remote

import android.util.Log
import com.raytalktech.gleamy.model.DataResponse
import com.raytalktech.gleamy.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        private val client = ApiConfig.getApiService()
        private const val TAG = "RemoteDataSource"


        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getOneCallWeather(
        lat: String,
        lon: String,
        callWeather: LoadOneCallWeather
    ) {
        client.getOneCallWeather(lat, lon).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                response.body()?.let { callWeather.onAllOneCallWeatherReceived(it) }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    interface LoadOneCallWeather {
        fun onAllOneCallWeatherReceived(mData: DataResponse)
    }
}
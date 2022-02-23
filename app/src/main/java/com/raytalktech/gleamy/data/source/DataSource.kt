package com.raytalktech.gleamy.data.source

import androidx.lifecycle.LiveData
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity
import com.raytalktech.gleamy.model.DataResponse

interface DataSource {

    fun getAllFavorite(): LiveData<List<DailyEntity>>

    fun insertDataFavorite(dataEntity: DailyEntity)

    fun deleteDataFavorite(dataEntity: DailyEntity)

    fun getOneCallWeather(lat: String, lon: String): LiveData<DataResponse>
}
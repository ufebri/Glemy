package com.raytalktech.gleamy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raytalktech.gleamy.data.source.DataRepository
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity
import com.raytalktech.gleamy.model.DataResponse

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getOneTimeCallWeather(lat: String, lon: String) : LiveData<DataResponse> = dataRepository.getOneCallWeather(lat, lon)

    fun addToFavorite(dailyEntity: DailyEntity) = dataRepository.insertDataFavorite(dailyEntity)
}
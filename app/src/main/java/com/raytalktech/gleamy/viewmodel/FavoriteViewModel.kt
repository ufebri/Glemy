package com.raytalktech.gleamy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raytalktech.gleamy.data.source.DataRepository
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity

class FavoriteViewModel(private val repository: DataRepository) :
    ViewModel() {

    fun deleteFavorite(dailyEntity: DailyEntity) = repository.deleteDataFavorite(dailyEntity)

    fun getAllFavorite(): LiveData<List<DailyEntity>> = repository.getAllFavorite()

}
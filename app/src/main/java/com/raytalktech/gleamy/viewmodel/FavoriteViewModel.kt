package com.raytalktech.gleamy.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raytalktech.gleamy.data.local.WeatherEntity
import com.raytalktech.gleamy.data.repository.Repository

class FavoriteViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    val favoriteDataList: LiveData<List<WeatherEntity?>?>?

    init {
        favoriteDataList = repository.getFavoriteListData()
    }
}
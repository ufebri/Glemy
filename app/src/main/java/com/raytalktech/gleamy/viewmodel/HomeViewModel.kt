package com.raytalktech.gleamy.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raytalktech.gleamy.data.repository.Repository
import com.raytalktech.gleamy.model.DataResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {


    private val lat = MutableLiveData<String>()
    private val lon = MutableLiveData<String>()
    private val _dataList = MutableLiveData<Result<DataResponse>>()
    val dataList = _dataList

    fun setLatLon(lat: String, lon: String) {
        this.lat.value = lat,
        this.lon.value = lon
    }

    init {
        fetchResponse(lat, lon)
    }

    private fun fetchResponse(lat: String, lon: String) {
        viewModelScope.launch {
            repository.fetchOneCallWeather(lat, lon).collect {
                _dataList.value = it
            }
        }
    }
}
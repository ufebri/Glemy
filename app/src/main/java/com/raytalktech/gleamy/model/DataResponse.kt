package com.raytalktech.gleamy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataResponse(
    var lat: Double,
    var lon: Double,
    var timezone: String,
    var current: Current,
    var daily: List<Daily>
) : Parcelable

@Parcelize
data class Current(
    var dt: Int,
    var temp: Double,
    var pressure: Int,
    var humidity: Int,
    var wind_speed: Double,
    var weather: List<Weather>
) : Parcelable

@Parcelize
data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
) : Parcelable

@Parcelize
data class Daily(
    var dt: Int,
    var temp: Temp,
    var weather: List<Weather>
) : Parcelable

@Parcelize
data class Temp(
    var day: Double,
    var night: Double
) : Parcelable
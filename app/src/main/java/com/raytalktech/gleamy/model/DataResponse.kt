package com.raytalktech.gleamy.model

class DataResponse(
    var lat: Double,
    var lon: Double,
    var timezone: String,
    var current: Current,
    var daily: ArrayList<Daily>
)

class Current(
    var dt: Int,
    var temp: Double,
    var pressure: Int,
    var humidity: Int,
    var wind_speed: Double,
    var weather: ArrayList<Weather>
)

class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

class Daily(
    var dt: Int,
    var temp: Temp,
    var weather: ArrayList<Weather>
)

class Temp(
    var day: Double,
    var night: Double
)
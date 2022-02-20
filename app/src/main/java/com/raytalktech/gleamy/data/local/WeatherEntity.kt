package com.raytalktech.gleamy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_main_table")
class WeatherEntity(
    @field:PrimaryKey(autoGenerate = true) var id: Int,
    var dt: Int?,
    var temp_day: Double?,
    var temp_night: Double?,
    var id_weather: Int?,
    var main_weather: String?,
    var description_weather: String?,
    var icon_weather: String?
)
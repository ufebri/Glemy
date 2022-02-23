package com.raytalktech.gleamy.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_daily_table")
class DailyEntity(
    @field:PrimaryKey(autoGenerate = true) var id: Int = 0,
    var daily_dt: Int,
    var daily_temp_day: Double,
    var daily_temp_night: Double,
    var id_weather: Int?,
    var main_weather: String?,
    var description_weather: String?,
    var icon_weather: String?
)
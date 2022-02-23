package com.raytalktech.gleamy.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.raytalktech.gleamy.Utils.CurrentDataConverters
import com.raytalktech.gleamy.Utils.DailyDataConverters
import com.raytalktech.gleamy.model.Current
import com.raytalktech.gleamy.model.Daily

//@Entity(tableName = "weather_current_table")
//class CurrentEntity(
//    @field:PrimaryKey(autoGenerate = true) var id: Int,
//    var lat: Double,
//    var lon: Double,
//    var timezone: String,
//    var current_dt: Int?,
//    var current_temp: Double,
//    var current_pressure: Int,
//    var current_humidity: Int,
//    var current_wind_speed: Double,
//    var id_weather: Int?,
//    var main_weather: String?,
//    var description_weather: String?,
//    var icon_weather: String?
//)

//@Entity(tableName = "weather_main_table")
//class MainData(
//    @field:PrimaryKey(autoGenerate = true) var id: Int,
//    var lat: Double,
//    var lon: Double,
//    var timezone: String,
//    @TypeConverters(CurrentDataConverters::class)
//    var current: List<Current>,
//    @TypeConverters(DailyDataConverters::class)
//    var daily: List<Daily>
//)

@Entity(tableName = "weather_daily_table")
class DailyEntity(
    @field:PrimaryKey(autoGenerate = true) var id: Int,
    var daily_dt: Int,
    var daily_temp_day: Double,
    var daily_temp_night: Double,
    var id_weather: Int?,
    var main_weather: String?,
    var description_weather: String?,
    var icon_weather: String?
)

//@Entity(tableName = "weather_main_table")
//class WeatherEntity(
//    @field:PrimaryKey(autoGenerate = true) var id: Int,
//    var weather_id: Int,
//    var weather_main: String,
//    var weather_description: String,
//    var weather_icon: String
//)
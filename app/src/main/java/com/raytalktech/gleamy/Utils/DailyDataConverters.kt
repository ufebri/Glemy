package com.raytalktech.gleamy.Utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raytalktech.gleamy.model.Daily

public class DailyDataConverters {

    /**
     * Convert a a list of data to a Json
     */
    @TypeConverter
    fun fromCurrentJson(stat: List<Daily>): String {
        return Gson().toJson(stat)
    }

    /**
     * Convert a json to a list of Images
     */
    @TypeConverter
    fun toCurrentList(json: String): List<Daily> {
        val notesType = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson<List<Daily>>(json, notesType)
    }
}
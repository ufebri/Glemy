package com.raytalktech.gleamy.Utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raytalktech.gleamy.model.Current

public class CurrentDataConverters {

    /**
     * Convert a a list of data to a Json
     */
    @TypeConverter
    fun fromCurrentJson(stat: List<Current>): String {
        return Gson().toJson(stat)
    }

    /**
     * Convert a json to a list of Images
     */
    @TypeConverter
    fun toCurrentList(json: String): List<Current> {
        val notesType = object : TypeToken<List<Current>>() {}.type
        return Gson().fromJson<List<Current>>(json, notesType)
    }
}
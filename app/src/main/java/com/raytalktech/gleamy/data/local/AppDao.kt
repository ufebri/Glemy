package com.raytalktech.gleamy.data.local

import androidx.room.*

@Dao
interface AppDao {

    @Query("SELECT * FROM weather_main_table")
    fun getAll(): List<WeatherEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: WeatherEntity)

    @Delete
    fun delete(mData: WeatherEntity)
}
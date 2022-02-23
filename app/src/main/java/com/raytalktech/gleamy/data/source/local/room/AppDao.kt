package com.raytalktech.gleamy.data.source.local.room

import androidx.room.*
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity

@Dao
interface AppDao {

    @Query("SELECT * FROM weather_daily_table")
    fun getAll(): List<DailyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DailyEntity)

    @Delete
    fun delete(mData: DailyEntity)
}
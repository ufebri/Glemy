package com.raytalktech.gleamy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    // public abstract MovieDao movieDao();
    abstract fun appDao(): AppDao
}
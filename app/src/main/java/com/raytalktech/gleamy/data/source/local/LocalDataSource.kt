package com.raytalktech.gleamy.data.source.local

import androidx.lifecycle.LiveData
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity
import com.raytalktech.gleamy.data.source.local.room.AppDao

class LocalDataSource(private val appDao: AppDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(appDao: AppDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(appDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getDataList(): LiveData<List<DailyEntity>> = appDao.getAll()

    fun insertData(dataEntity: DailyEntity) = appDao.insert(dataEntity)

    fun deleteData(dataEntity: DailyEntity) = appDao.delete(dataEntity)
}
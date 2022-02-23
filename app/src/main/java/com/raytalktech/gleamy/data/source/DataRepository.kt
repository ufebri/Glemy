package com.raytalktech.gleamy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raytalktech.gleamy.Utils.AppExecutors
import com.raytalktech.gleamy.data.source.local.LocalDataSource
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity
import com.raytalktech.gleamy.data.source.remote.RemoteDataSource
import com.raytalktech.gleamy.model.DataResponse

class DataRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource
) : DataSource {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(
                    localDataSource,
                    appExecutors,
                    remoteData
                )
            }
    }

    override fun getAllFavorite(): List<DailyEntity> {
        return localDataSource.getDataList()
    }

    override fun insertDataFavorite(dataEntity: DailyEntity) {
        return appExecutors.diskIO().execute {
            localDataSource.insertData(dataEntity)
        }
    }

    override fun deleteDataFavorite(dataEntity: DailyEntity) {
        return appExecutors.diskIO().execute {
            localDataSource.deleteData(dataEntity)
        }
    }

    override fun getOneCallWeather(lat: String, lon: String): LiveData<DataResponse> {
        val data = MutableLiveData<DataResponse>()
        remoteDataSource.getOneCallWeather(lat, lon, object : RemoteDataSource.LoadOneCallWeather {
            override fun onAllOneCallWeatherReceived(mData: DataResponse) {
                data.postValue(mData)
            }
        })
        return data
    }


}
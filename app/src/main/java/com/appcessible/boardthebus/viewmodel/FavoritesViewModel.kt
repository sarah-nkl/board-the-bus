package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.BusStop
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val database: AppDatabase
) : ViewModel() {

    private val busStopsLiveData = MutableLiveData<List<BusStop>>(emptyList())

    suspend fun retrieveFavorites() {
        busStopsLiveData.postValue(database.favoriteBusStopDao().getAllFavoriteBusStops().map {
            BusStop(it.busStopNo, it.roadName, it.description, it.longitude, it.longitude)
        })
    }
    suspend fun removeFromFavorites(busStopNo: String) {
        database.favoriteBusStopDao().delete(busStopNo)
    }

    fun getBusStopsLiveData(): LiveData<List<BusStop>> {
        return busStopsLiveData
    }
}


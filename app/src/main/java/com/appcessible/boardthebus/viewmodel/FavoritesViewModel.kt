package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.model.BusService
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val busArrivalService: BusArrivalService,
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

    suspend fun searchBusArrival(queryString: String): List<BusService> {
        return busArrivalService.getBusArrivalByBus(queryString).Services ?: emptyList()
    }

    fun getBusStopsLiveData(): LiveData<List<BusStop>> {
        return busStopsLiveData
    }
}


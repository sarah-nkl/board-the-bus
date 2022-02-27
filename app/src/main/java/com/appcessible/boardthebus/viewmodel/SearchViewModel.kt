package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.JsonUtil
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.model.BusService
import kotlinx.coroutines.launch


class SearchViewModel(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase,
    private val jsonUtil: JsonUtil
) : ViewModel() {

    private val busStopsLiveData = MutableLiveData<List<BusStop>>(emptyList())

//    suspend fun search(queryString: String): List<BusService>? {
//        val busArrival = busArrivalService.getBusArrivalByBus(queryString)
//        return busArrival.Services
//    }

    suspend fun search(queryString: String) {
        val busStops = database.busStopDao().loadAllByIds(listOf(queryString))
        busStopsLiveData.postValue(busStops)
    }

    suspend fun addToFavorites(busStop: BusStop) {
        database.busStopDao().addToFavorite(busStop.busStopNo)
    }

    suspend fun removeFromFavorites(busStop: BusStop) {
        database.busStopDao().removeFromFavorite(busStop.busStopNo)
    }

    fun getBusStopsLiveData(): LiveData<List<BusStop>> {
        return busStopsLiveData
    }
}

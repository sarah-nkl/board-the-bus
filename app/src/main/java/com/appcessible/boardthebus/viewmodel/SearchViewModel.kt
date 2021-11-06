package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.JsonUtil
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.FavoriteBus
import com.appcessible.boardthebus.model.BusService


class SearchViewModel(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase,
    private val jsonUtil: JsonUtil
) : ViewModel() {

//    suspend fun search(queryString: String): List<BusService>? {
//        val busArrival = busArrivalService.getBusArrivalByBus(queryString)
//        return busArrival.Services
//    }

    suspend fun search(queryString: String): List<BusService> {
        val busArrival = jsonUtil.getMockBusArrival()
        val favoriteBuses = database.favoriteBusDao().loadByBusStopId("83139").map {
            it.busNo
        }
        return busArrival.Services!!.map { bus ->
            if (favoriteBuses.contains(bus.ServiceNo)) {
                bus.copy(isFavorite = true)
            } else {
                bus
            }
        }
    }

    suspend fun addToFavorites(busStop: FavoriteBus) {
        database.favoriteBusDao().insertAll(busStop)
    }

    suspend fun removeFromFavorites(busStop: FavoriteBus) {
        database.favoriteBusStopDao().delete(busStop)
    }
}

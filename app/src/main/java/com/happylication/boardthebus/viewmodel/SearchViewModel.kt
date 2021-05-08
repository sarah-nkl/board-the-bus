package com.happylication.boardthebus.viewmodel

import androidx.lifecycle.ViewModel
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.JsonUtil
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.database.entity.FavoriteBus
import com.happylication.boardthebus.model.BusService


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

package com.happylication.boardthebus.viewmodel

import androidx.lifecycle.ViewModel
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.database.entity.FavoriteBus
import com.happylication.boardthebus.model.BusService


class SearchViewModel(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase
) : ViewModel() {

    suspend fun search(queryString: String): List<BusService>? {
        val busArrival = busArrivalService.getBusArrivalByBus(queryString)
        return busArrival.Services
    }

    suspend fun addToFavorites(busStop: FavoriteBus) {
        database.favoriteBusDao().insertAll(busStop)
    }
}

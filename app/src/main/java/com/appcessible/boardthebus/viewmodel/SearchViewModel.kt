package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.JsonUtil
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.SearchResult
import com.appcessible.boardthebus.model.SearchResultLabel

class SearchViewModel(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase,
    private val jsonUtil: JsonUtil
) : ViewModel() {

    private val searchResultsLiveData = MutableLiveData<List<SearchResult>>(emptyList())
    private val busArrivalResultsLiveData = MutableLiveData<List<BusService>?>(emptyList())

    suspend fun searchBusArrival(queryString: String) {
        val busArrival = busArrivalService.getBusArrivalByBus(queryString)
        busArrivalResultsLiveData.postValue(busArrival.Services)
    }

    suspend fun search(queryString: String) {
        val result = mutableListOf<SearchResult>()
        result.addAll(database.busStopDao().loadById(queryString).map {
            SearchResult(it.busStopNo, it.busStopNo, SearchResultLabel.BUS_STOP)
        })
        result.addAll(database.busStopDao().loadByName(queryString).map {
            SearchResult(it.busStopNo, it.description, SearchResultLabel.BUS_STOP)
        })
        result.addAll(database.busDao().loadById(queryString).map {
            SearchResult(it.busNo, it.busNo, SearchResultLabel.BUS_SERVICE)
        })
        searchResultsLiveData.postValue(result)
    }

    suspend fun addToFavorites(busStop: BusStop) {
        database.busStopDao().addToFavorite(busStop.busStopNo)
    }

    suspend fun removeFromFavorites(busStop: BusStop) {
        database.busStopDao().removeFromFavorite(busStop.busStopNo)
    }

    fun getSearchResultsLiveData(): LiveData<List<SearchResult>> {
        return searchResultsLiveData
    }

    fun getBusArrivalResultsLiveData(): LiveData<List<BusService>?> {
        return busArrivalResultsLiveData
    }
}

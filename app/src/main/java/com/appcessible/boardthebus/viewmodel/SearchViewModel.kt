package com.appcessible.boardthebus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.SearchResult
import com.appcessible.boardthebus.model.SearchResultLabel

class SearchViewModel(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase
) : ViewModel() {

    private val searchResultsLiveData = MutableLiveData<List<SearchResult>>(emptyList())
    private val busArrivalResultsLiveData = MutableLiveData<List<BusService>?>(emptyList())

    suspend fun searchBusArrival(queryString: String) {
        val busArrival = busArrivalService.getBusArrivalByBus(queryString)
        busArrivalResultsLiveData.postValue(busArrival.Services)
    }

    suspend fun search(queryString: String) {
        Log.d(SearchViewModel::class.java.simpleName, "Searching for $queryString")
        val result = mutableListOf<SearchResult>()
        result.addAll(database.busStopDao().loadById(queryString).map {
            SearchResult(it.busStopNo, it.busStopNo, SearchResultLabel.BUS_STOP, it.isFavorite)
        })
        result.addAll(database.busStopDao().loadByName(queryString).map {
            SearchResult(it.busStopNo, it.description, SearchResultLabel.BUS_STOP, it.isFavorite)
        })
        result.addAll(database.busDao().loadById(queryString).map {
            SearchResult(it.busNo, it.busNo, SearchResultLabel.BUS_SERVICE)
        })
        searchResultsLiveData.postValue(result)
    }

    suspend fun updateFavorites(busStopNo: String) {
        val resultList = database.busStopDao().loadById(busStopNo)
        if (resultList.isEmpty() || resultList.size > 1) {
            throw IllegalStateException("Unable to update favorites")
        }
        if (resultList[0].isFavorite) {
            database.busStopDao().removeFromFavorite(busStopNo)
        } else {
            database.busStopDao().addToFavorite(busStopNo)
        }
    }

    fun getSearchResultsLiveData(): LiveData<List<SearchResult>> {
        return searchResultsLiveData
    }

    fun getBusArrivalResultsLiveData(): LiveData<List<BusService>?> {
        return busArrivalResultsLiveData
    }
}

package com.appcessible.boardthebus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.FavoriteBusStop
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.SearchResult
import com.appcessible.boardthebus.model.SearchResultLabel

class SearchViewModel(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase
) : ViewModel() {

    private val searchResultsLiveData = MutableLiveData<List<SearchResult>>(emptyList())
    private val busArrivalResultsLiveData = MutableLiveData<List<BusService>?>(emptyList())
    private val favoriteBusStopLiveData = MutableLiveData(false)
    private val currentBusStopLiveData = MutableLiveData<String?>(null)

    suspend fun searchBusArrival(queryString: String) {
        val busArrival = busArrivalService.getBusArrivalByBus(queryString)
        busArrivalResultsLiveData.postValue(busArrival.Services)
        currentBusStopLiveData.postValue(queryString)
        favoriteBusStopLiveData.postValue(database.favoriteBusStopDao().loadById(queryString).isNotEmpty())
    }

    suspend fun search(queryString: String) {
        Log.d(SearchViewModel::class.java.simpleName, "Searching for $queryString")
        currentBusStopLiveData.postValue(null)
        if (queryString.isEmpty()) {
            searchResultsLiveData.postValue(emptyList())
            return
        }
        val result = mutableListOf<SearchResult>()
        result.addAll(database.busDao().loadById(queryString).map {
            SearchResult(it.busNo, it.busNo, SearchResultLabel.BUS_SERVICE)
        })
        result.addAll(database.busStopDao().loadById(queryString).map {
            SearchResult(it.busStopNo, it.busStopNo, SearchResultLabel.BUS_STOP)
        })
        result.addAll(database.busStopDao().loadByName(queryString).map {
            SearchResult(it.busStopNo, it.description, SearchResultLabel.BUS_STOP)
        })
        result.addAll(database.busStopDao().loadByRoadName(queryString).map {
            SearchResult(it.busStopNo, it.roadName, SearchResultLabel.ADDRESS)
        })
        searchResultsLiveData.postValue(result)
    }

    suspend fun updateFavorites(busStopNo: String) {
        val resultList = database.favoriteBusStopDao().loadById(busStopNo)
        if (resultList.size > 1) {
            throw IllegalStateException("Unable to update favorites")
        }
        if (resultList.isEmpty()) {
            database.favoriteBusStopDao().insert(FavoriteBusStop(busStopNo))
        } else {
            database.favoriteBusStopDao().delete(busStopNo)
        }
        favoriteBusStopLiveData.postValue(resultList.isEmpty())
    }

    fun getSearchResultsLiveData(): LiveData<List<SearchResult>> {
        return searchResultsLiveData
    }

    fun getBusArrivalResultsLiveData(): LiveData<List<BusService>?> {
        return busArrivalResultsLiveData
    }

    fun getFavoriteBusStopLiveData(): LiveData<Boolean> {
        return favoriteBusStopLiveData
    }

    fun getCurrentBusStopLiveData(): LiveData<String?> {
        return currentBusStopLiveData
    }
}

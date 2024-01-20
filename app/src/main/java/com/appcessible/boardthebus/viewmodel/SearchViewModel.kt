package com.appcessible.boardthebus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.database.entity.FavoriteBusStop
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.SearchResult
import com.appcessible.boardthebus.model.SearchResultLabel
import kotlinx.coroutines.launch

class SearchViewModel(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase
) : ViewModel() {

    private val searchResultsLiveData = MutableLiveData<List<SearchResult>>(emptyList())
    private val busArrivalResultsLiveData = MutableLiveData<List<BusService>?>(emptyList())
    private val isFavoriteBusStopLiveData = MutableLiveData(false)
    private val currentBusStopLiveData = MutableLiveData<BusStop?>(null)

    suspend fun searchBusArrival(queryString: String) {
        val busArrival = busArrivalService.getBusArrivalByBus(queryString)
        busArrivalResultsLiveData.postValue(busArrival.Services)
        currentBusStopLiveData.postValue(database.busStopDao().loadById(queryString)[0])
        isFavoriteBusStopLiveData.postValue(database.favoriteBusStopDao().loadById(queryString).isNotEmpty())
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
            SearchResult(it.busNo, it.busNo, it.busNo, SearchResultLabel.BUS_SERVICE)
        })
        result.addAll(database.busStopDao().loadById(queryString).map {
            SearchResult(it.busStopNo, it.busStopNo, it.description, SearchResultLabel.BUS_STOP)
        })
        result.addAll(database.busStopDao().loadBusStopsWithQuery(queryString).map {
            SearchResult(it.busStopNo, it.description, it.roadName, SearchResultLabel.ADDRESS)
        })
        searchResultsLiveData.postValue(result)
    }

    fun updateFavorites() {
        viewModelScope.launch {
            val busStop = currentBusStopLiveData.value
                ?: throw IllegalStateException("Unable to update favorites")

            val resultList = database.favoriteBusStopDao().loadById(busStop.busStopNo)
            if (resultList.size > 1) {
                throw IllegalStateException("Unable to update favorites")
            }
            if (resultList.isEmpty()) {
                database.favoriteBusStopDao().insert(FavoriteBusStop(busStop.busStopNo))
            } else {
                database.favoriteBusStopDao().delete(busStop.busStopNo)
            }
            isFavoriteBusStopLiveData.postValue(resultList.isEmpty())
        }
    }

    suspend fun checkIsFavorite() {
        val busStop = currentBusStopLiveData.value
        if (busStop == null) {
            isFavoriteBusStopLiveData.postValue(false)
            return
        }
        val resultList = database.favoriteBusStopDao().loadById(busStop.busStopNo)
        if (resultList.size > 1) {
            throw IllegalStateException("Duplicate bus stop entries in Favorites DB")
        }
        isFavoriteBusStopLiveData.postValue(resultList.isNotEmpty())
    }

    fun getSearchResultsLiveData(): LiveData<List<SearchResult>> {
        return searchResultsLiveData
    }

    fun getBusArrivalResultsLiveData(): LiveData<List<BusService>?> {
        return busArrivalResultsLiveData
    }

    fun getFavoriteBusStopLiveData(): LiveData<Boolean> {
        return isFavoriteBusStopLiveData
    }

    fun getCurrentBusStopLiveData(): LiveData<BusStop?> {
        return currentBusStopLiveData
    }
}

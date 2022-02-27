package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.adapters.FavoritesAdapter
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop
import javax.inject.Inject

class FavoritesFragmentViewModel @Inject constructor(
    database: AppDatabase
) : ViewModel() {

    val busStops: LiveData<List<BusStop>> = database.busStopDao().getAllFavorite()

    val adapter = FavoritesAdapter(emptyList())
}


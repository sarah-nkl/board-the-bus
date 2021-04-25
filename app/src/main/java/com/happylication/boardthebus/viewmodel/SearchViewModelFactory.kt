package com.happylication.boardthebus.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.database.entity.FavoriteBus
import com.happylication.boardthebus.model.BusArrival
import javax.inject.Inject

class SearchViewModelFactory(
    private val busArrivalService: BusArrivalService,
    private val database: AppDatabase,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return SearchViewModel(busArrivalService, database) as T
    }
}

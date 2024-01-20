package com.appcessible.boardthebus.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.database.AppDatabase

@Suppress("UNCHECKED_CAST")
class FavoritesViewModelFactory(
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
        return FavoritesViewModel(busArrivalService, database) as T
    }
}

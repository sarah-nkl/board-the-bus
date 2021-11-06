package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.adapters.FavoritesAdapter
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.FavoriteBus
import javax.inject.Inject

class FavoritesFragmentViewModel @Inject constructor(
    database: AppDatabase
) : ViewModel() {

    val favoriteBuses: LiveData<List<FavoriteBus>> = database.favoriteBusDao().getAll()

    val adapter = FavoritesAdapter(emptyList())
}


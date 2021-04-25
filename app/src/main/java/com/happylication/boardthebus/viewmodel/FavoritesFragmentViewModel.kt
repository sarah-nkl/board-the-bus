package com.happylication.boardthebus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.happylication.boardthebus.adapters.FavoritesAdapter
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.database.entity.FavoriteBus
import javax.inject.Inject

class FavoritesFragmentViewModel @Inject constructor(
    database: AppDatabase
) : ViewModel() {

    val favoriteBuses: LiveData<List<FavoriteBus>> = database.favoriteBusDao().getAll()

    val adapter = FavoritesAdapter(emptyList())
}


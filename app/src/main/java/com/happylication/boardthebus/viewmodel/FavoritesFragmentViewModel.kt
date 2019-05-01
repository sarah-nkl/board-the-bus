package com.happylication.boardthebus.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.database.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FavoritesFragmentViewModel @Inject constructor(
    database: AppDatabase
) {



}
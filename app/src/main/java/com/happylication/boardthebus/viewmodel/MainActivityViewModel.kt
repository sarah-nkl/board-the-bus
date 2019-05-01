package com.happylication.boardthebus.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.happylication.boardthebus.BusArrivalService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val busArrivalService: BusArrivalService,
    lifecycle: Lifecycle
) {

    val text = ObservableField<String>()
    private val subscriptions = CompositeDisposable()

    init {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                subscriptions.clear()
            }
        })
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                retrieveInfo()
            }
        })
    }

    private fun retrieveInfo() {
        subscriptions.add(busArrivalService.getBusArrivalByBus("10111", "123")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    text.set(it.Services?.getOrNull(0)?.NextBus?.EstimatedArrival)
                },
                onError = {
                    Log.w("BusArrival", "Unable to retrieve info", it)
                }
            ))
    }

}
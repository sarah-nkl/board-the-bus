package com.appcessible.boardthebus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcessible.boardthebus.model.BusStops

class BusArrivalsViewModel(busStop: BusStops, private val closeDialogListener: () -> Unit) : ViewModel() {
    val currentBusStop = MutableLiveData(busStop)

    fun closeDialog() {
        closeDialogListener()
    }
}

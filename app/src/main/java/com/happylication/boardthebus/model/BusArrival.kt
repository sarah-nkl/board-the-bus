package com.happylication.boardthebus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusArrival(
    val BusStopCode: String,
    val Services: List<BusService>?
) : Parcelable
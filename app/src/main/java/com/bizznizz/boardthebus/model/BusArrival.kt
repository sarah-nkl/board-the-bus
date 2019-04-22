package com.bizznizz.boardthebus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusArrival(
    val BusStopCode: String,
    val Services: List<BusService>?
) : Parcelable
package com.appcessible.boardthebus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStopsResponse(
    val value: List<BusStops>?
) : Parcelable
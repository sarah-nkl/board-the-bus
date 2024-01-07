package com.appcessible.boardthebus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStops(
    val BusStopCode: String,
    val RoadName: String,
    val Description: String,
    val Latitude: Float,
    val Longitude: Float,
    val isFavorite: Boolean
) : Parcelable
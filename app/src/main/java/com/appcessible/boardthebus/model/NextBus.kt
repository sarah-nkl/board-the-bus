package com.appcessible.boardthebus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NextBus(
    val OriginCode: String,
    val DestinationCode: String,
    val EstimatedArrival: String,
    val Latitude: String,
    val Longitude: String,
    val VisitNumber: String,
    val Load: String,
    val Feature: String,
    val Type: String
) : Parcelable

enum class BusLoad(private val load: String) {
    SEATS_AVAILABLE("SEA"),
    STANDING_AVAILABLE("SDA"),
    LIMITED_STANDING_AVAILABLE("LSD");

    companion object {
        fun getByString(query: String) = BusLoad.values().firstOrNull { it.load == query }
    }
}
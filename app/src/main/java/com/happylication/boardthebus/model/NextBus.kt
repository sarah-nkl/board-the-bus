package com.happylication.boardthebus.model

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

val mockNextBus = NextBus(
    OriginCode = "12345",
    DestinationCode = "54321",
    EstimatedArrival = "21:01",
    Latitude = "1.232424",
    Longitude = "0.3534535",
    VisitNumber = "4",
    Load = "23",
    Feature = "NA",
    Type = "NA"
)

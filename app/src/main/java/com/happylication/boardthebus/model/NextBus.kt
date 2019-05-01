package com.happylication.boardthebus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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

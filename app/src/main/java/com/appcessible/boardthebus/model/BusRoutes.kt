package com.appcessible.boardthebus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusRoutes(
    val ServiceNo: String,
    val Operator: String,
    val Direction: Int,
    val StopSequence: Int,
    val BusStopCode: String
) : Parcelable
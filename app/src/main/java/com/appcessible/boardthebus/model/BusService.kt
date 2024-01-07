package com.appcessible.boardthebus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusService(
    val ServiceNo: String,
    val Direction: Int, // The direction in which the bus travels (1 or 2), loop services only have 1 direction
    val Operator: String,
    val NextBus: NextBus?,
    val NextBus2: NextBus?,
    val NextBus3: NextBus?
) : Parcelable
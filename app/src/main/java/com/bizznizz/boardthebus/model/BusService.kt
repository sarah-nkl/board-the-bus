package com.bizznizz.boardthebus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusService(
    val ServiceNo: String,
    val Operator: String,
    val NextBus: NextBus?,
    val NextBus2: NextBus?,
    val NextBus3: NextBus?
) : Parcelable
package com.appcessible.boardthebus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusServicesResponse(
    val value: List<BusService>?
) : Parcelable
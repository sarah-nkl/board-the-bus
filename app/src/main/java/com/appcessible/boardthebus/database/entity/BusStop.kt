package com.appcessible.boardthebus.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusStop(
    @PrimaryKey val busStopNo: String,
    val roadName: String,
    val description: String,
    val longitude: Float,
    val latitude: Float
)
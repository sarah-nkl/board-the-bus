package com.appcessible.boardthebus.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteBusStop(
    @PrimaryKey val busStopNo: String
)
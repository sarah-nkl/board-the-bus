package com.happylication.boardthebus.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["busNo", "busStopNo"])
data class FavoriteBus(
    val busNo: String,
    val busStopNo: String
)
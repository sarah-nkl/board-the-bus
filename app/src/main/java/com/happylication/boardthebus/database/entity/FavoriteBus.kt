package com.happylication.boardthebus.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteBus(
    @PrimaryKey val busNo: Int
)
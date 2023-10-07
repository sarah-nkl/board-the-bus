package com.appcessible.boardthebus.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bus(
    @PrimaryKey val busNo: String
)
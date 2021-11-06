package com.appcessible.boardthebus.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appcessible.boardthebus.database.dao.FavoriteBusDao
import com.appcessible.boardthebus.database.dao.FavoriteBusStopDao
import com.appcessible.boardthebus.database.entity.FavoriteBus
import com.appcessible.boardthebus.database.entity.FavoriteBusStop

@Database(entities = [FavoriteBus::class, FavoriteBusStop::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBusDao(): FavoriteBusDao
    abstract fun favoriteBusStopDao(): FavoriteBusStopDao
}
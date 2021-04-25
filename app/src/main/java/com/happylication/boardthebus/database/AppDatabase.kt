package com.happylication.boardthebus.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.happylication.boardthebus.database.dao.FavoriteBusDao
import com.happylication.boardthebus.database.dao.FavoriteBusStopDao
import com.happylication.boardthebus.database.entity.FavoriteBus
import com.happylication.boardthebus.database.entity.FavoriteBusStop

@Database(entities = [FavoriteBus::class, FavoriteBusStop::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBusDao(): FavoriteBusDao
    abstract fun favoriteBusStopDao(): FavoriteBusStopDao
}
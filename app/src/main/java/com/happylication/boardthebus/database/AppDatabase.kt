package com.happylication.boardthebus.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.happylication.boardthebus.database.dao.FavoriteBusDao
import com.happylication.boardthebus.database.entity.FavoriteBus

@Database(entities = [FavoriteBus::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBusDao(): FavoriteBusDao
}
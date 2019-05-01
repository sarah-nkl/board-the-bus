package com.happylication.boardthebus.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.happylication.boardthebus.database.entity.FavoriteBus

@Dao
interface FavoriteBusDao {

    @Query("SELECT * FROM favoritebus")
    fun getAll(): List<FavoriteBus>

    @Query("SELECT * FROM favoritebus WHERE busNo IN (:busNos)")
    fun loadAllByIds(busNos: IntArray): List<FavoriteBus>

    @Insert
    fun insertAll(vararg buses: FavoriteBus)

    @Delete
    fun delete(bus: FavoriteBus)
}
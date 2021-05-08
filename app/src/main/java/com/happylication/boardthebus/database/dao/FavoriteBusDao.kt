package com.happylication.boardthebus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.happylication.boardthebus.database.entity.FavoriteBus

@Dao
interface FavoriteBusDao {

    @Query("SELECT * FROM favoritebus")
    fun getAll(): LiveData<List<FavoriteBus>>

    @Query("SELECT * FROM favoritebus WHERE busNo IN (:busNos)")
    fun loadAllByIds(busNos: IntArray): List<FavoriteBus>

    @Query("SELECT * FROM favoritebus WHERE busStopNo = :busStopNo")
    suspend fun loadByBusStopId(busStopNo: String): List<FavoriteBus>

    @Insert
    suspend fun insertAll(vararg buses: FavoriteBus)

    @Delete
    fun delete(bus: FavoriteBus)
}
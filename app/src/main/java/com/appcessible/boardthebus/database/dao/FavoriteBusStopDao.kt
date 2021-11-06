package com.appcessible.boardthebus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.appcessible.boardthebus.database.entity.FavoriteBus
import com.appcessible.boardthebus.database.entity.FavoriteBusStop

@Dao
interface FavoriteBusStopDao {

    @Query("SELECT * FROM favoritebusstop")
    fun getAll(): LiveData<List<FavoriteBusStop>>

    @Query("SELECT * FROM favoritebusstop WHERE busStopNo IN (:busStopNos)")
    fun loadAllByIds(busStopNos: IntArray): List<FavoriteBusStop>

    @Insert
    fun insertAll(vararg buses: FavoriteBusStop)

    @Delete
    suspend fun delete(bus: FavoriteBus)
}
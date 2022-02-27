package com.appcessible.boardthebus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop

@Dao
interface BusStopDao {

    @Query("SELECT * FROM busstop")
    fun getAll(): LiveData<List<BusStop>>

    @Query("SELECT * FROM BusStop WHERE busStopNo IN (:busStopNos)")
    suspend fun loadAllByIds(busStopNos: List<String>): List<BusStop>

    @Insert
    fun insertAll(buses: List<BusStop>)

    @Query("SELECT * FROM busstop WHERE isFavorite = 1")
    fun getAllFavorite(): LiveData<List<BusStop>>

    @Delete
    suspend fun delete(busStop: BusStop)

    @Query("UPDATE busstop SET isFavorite = 0 WHERE busStopNo = :id")
    suspend fun removeFromFavorite(id: String)

    @Query("UPDATE busstop SET isFavorite = 1 WHERE busStopNo = :id")
    suspend fun addToFavorite(id: String)
}
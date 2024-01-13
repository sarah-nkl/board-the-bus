package com.appcessible.boardthebus.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.database.entity.FavoriteBusStop

@Dao
interface FavoriteBusStopDao {

    @Query("SELECT * FROM BusStop " +
            "INNER JOIN FavoriteBusStop ON BusStop.busStopNo = FavoriteBusStop.busStopNo")
    suspend fun getAllFavoriteBusStops(): List<BusStop>

    @Query("SELECT * FROM FavoriteBusStop " +
            "WHERE FavoriteBusStop.busStopNo = :busStopNo")
    suspend fun loadById(busStopNo: String): List<FavoriteBusStop>

    @Query("DELETE FROM FavoriteBusStop WHERE FavoriteBusStop.busStopNo = :busStopNo")
    suspend fun delete(busStopNo: String)

    @Insert
    suspend fun insert(favoriteBusStop: FavoriteBusStop)
}
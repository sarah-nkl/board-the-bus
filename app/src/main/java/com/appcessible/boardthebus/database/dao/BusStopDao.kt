package com.appcessible.boardthebus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop

@Dao
interface BusStopDao {

    @Query("SELECT * FROM BusStop")
    fun getAll(): LiveData<List<BusStop>>

    @Query("SELECT * FROM BusStop WHERE busStopNo IN (:busStopNos)")
    suspend fun loadAllByIds(busStopNos: List<String>): List<BusStop>

    @Query("SELECT * FROM BusStop WHERE busStopNo LIKE '%' || :busStopNo || '%'")
    suspend fun loadById(busStopNo: String): List<BusStop>

    @Query("SELECT * FROM BusStop WHERE description LIKE '%' || :busStopName || '%'")
    suspend fun loadByName(busStopName: String): List<BusStop>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(buses: List<BusStop>)

    @Query("SELECT * FROM BusStop WHERE isFavorite = 1")
    suspend fun getAllFavorite(): List<BusStop>

    @Delete
    suspend fun delete(busStop: BusStop)

    @Query("UPDATE BusStop SET isFavorite = 0 WHERE busStopNo = :id")
    suspend fun removeFromFavorite(id: String)

    @Query("UPDATE BusStop SET isFavorite = 1 WHERE busStopNo = :id")
    suspend fun addToFavorite(id: String)
}
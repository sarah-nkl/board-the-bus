package com.appcessible.boardthebus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appcessible.boardthebus.database.entity.BusStop

@Dao
interface BusStopDao {

    @Query("SELECT * FROM BusStop")
    fun getAll(): LiveData<List<BusStop>>

    @Query("SELECT * FROM BusStop WHERE busStopNo IN (:busStopNos)")
    suspend fun loadAllByIds(busStopNos: List<String>): List<BusStop>

    @Query("SELECT * FROM BusStop WHERE busStopNo LIKE '%' || :busStopNo || '%'")
    suspend fun loadById(busStopNo: String): List<BusStop>

    @Query("SELECT * FROM BusStop WHERE description LIKE '%' || :description || '%'")
    suspend fun loadByName(description: String): List<BusStop>

    @Query("SELECT * FROM BusStop WHERE roadName LIKE '%' || :roadName || '%'")
    suspend fun loadByRoadName(roadName: String): List<BusStop>

    @Query("SELECT * FROM BusStop WHERE description LIKE '%' || :search || '%'" +
            "OR roadName LIKE '%' || :search || '%'")
    suspend fun loadBusStopsWithQuery(search: String): List<BusStop>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(buses: List<BusStop>)

    @Delete
    suspend fun delete(busStop: BusStop)

    @Query("DELETE FROM BusStop")
    suspend fun deleteAll()
}
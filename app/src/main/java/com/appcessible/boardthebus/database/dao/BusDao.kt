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
interface BusDao {

    @Query("SELECT * FROM bus")
    fun getAll(): LiveData<List<Bus>>

    @Query("SELECT * FROM bus WHERE busNo IN (:busNos)")
    fun loadAllByIds(busNos: IntArray): List<Bus>

    @Query("SELECT * FROM Bus WHERE busNo LIKE '%' || :busServiceId || '%'")
    suspend fun loadById(busServiceId: String): List<Bus>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(buses: List<Bus>)

    @Delete
    fun delete(bus: Bus)
}
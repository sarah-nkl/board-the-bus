package com.appcessible.boardthebus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.appcessible.boardthebus.database.entity.Bus

@Dao
interface BusDao {

    @Query("SELECT * FROM bus")
    fun getAll(): LiveData<List<Bus>>

    @Query("SELECT * FROM bus WHERE busNo IN (:busNos)")
    fun loadAllByIds(busNos: IntArray): List<Bus>

    @Insert
    suspend fun insertAll(buses: List<Bus>)

    @Delete
    fun delete(bus: Bus)
}
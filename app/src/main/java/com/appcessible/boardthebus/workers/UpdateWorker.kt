package com.appcessible.boardthebus.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.MAX_RESPONSE_SIZE
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateWorker(
    private val appContext: Context,
    workerParams: WorkerParameters,
    private val service: BusArrivalService,
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d("UpdateWorker", "Updating bus stop list in DB")
            val busStopList = mutableListOf<BusStop>()
            val busList = mutableListOf<Bus>()

            var prevBusStopListSize = -1
            var busStopServiceSkipSize = 0
            while (busStopList.size > prevBusStopListSize) {
                prevBusStopListSize = busStopList.size
                busStopList.addAll(service.getBusStops(busStopServiceSkipSize).value!!.map {
                    BusStop(it.BusStopCode, it.RoadName, it.Description, it.Longitude, it.Latitude)
                })
                busStopServiceSkipSize += MAX_RESPONSE_SIZE
            }

            var prevBusListSize = -1
            var busServiceSkipSize = 0
            while (busList.size > prevBusListSize) {
                prevBusListSize = busStopList.size
                busList.addAll(service.getBusServices(busServiceSkipSize).value!!.map {
                    Bus(it.ServiceNo)
                })
                busServiceSkipSize += MAX_RESPONSE_SIZE
            }

            val database = AppDatabase.getInstance(appContext)

            // Clear all
            database.busStopDao().deleteAll()
            database.busDao().deleteAll()

            // Insert all
            database.busStopDao().insertAll(busStopList)
            database.busDao().insertAll(busList)
        } catch (e: Exception) {
            Log.e("UpdateWorker", "UpdateWorker failed", e)
            Result.failure()
        }

        Result.success()
    }
}
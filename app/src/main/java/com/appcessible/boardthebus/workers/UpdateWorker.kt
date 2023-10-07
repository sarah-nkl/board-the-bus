package com.appcessible.boardthebus.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class UpdateWorker(
    private val appContext: Context,
    workerParams: WorkerParameters,
    private val service: BusArrivalService,
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val busStops = service.getBusStops().value
            val buses = service.getBusServices().value
            val database = AppDatabase.getInstance(appContext)
            val busStopList = busStops!!.map {
                BusStop(it.BusStopCode, it.RoadName, it.Description, it.Longitude, it.Latitude, false)
            }
            val busList = buses!!.map {
                Bus(it.ServiceNo)
            }
            database.busStopDao().insertAll(busStopList)
            database.busDao().insertAll(busList)
        } catch (e: Exception) {
            Log.e("UpdateWorker", "UpdateWorker failed", e)
            Result.failure()
        }

        Result.success()
    }
}
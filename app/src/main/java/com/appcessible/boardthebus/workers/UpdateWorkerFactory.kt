package com.appcessible.boardthebus.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.appcessible.boardthebus.BusArrivalService

class UpdateWorkerFactory(
    private val service: BusArrivalService
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            UpdateWorker::class.java.name ->
                UpdateWorker(appContext, workerParameters, service)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }
}
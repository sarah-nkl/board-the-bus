package com.appcessible.boardthebus.workers

import androidx.work.DelegatingWorkerFactory
import com.appcessible.boardthebus.BusArrivalService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusWorkerFactory @Inject constructor(
    service: BusArrivalService
) : DelegatingWorkerFactory() {

    init {
        addFactory(UpdateWorkerFactory(service))
        // Add here other factories that you may need in your application
    }
}
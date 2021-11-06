package com.appcessible.boardthebus

import com.appcessible.boardthebus.model.BusArrival
import retrofit2.http.GET
import retrofit2.http.Query

private const val BUS_STOP_CODE = "BusStopCode"
private const val SERVICE_NO = "ServiceNo"

interface BusArrivalService {

    @GET("BusArrivalv2")
    suspend fun getBusArrivalByBus(
        @Query(BUS_STOP_CODE) busStopCode: String,
        @Query(SERVICE_NO) serviceNo: String? = null
    ): BusArrival

    /**
    Returns detailed route information for all services currently in operation,
    including: all bus stops along each route, first/last bus timings for each stop.
     **/
    @GET("BusRoutes")
    suspend fun getBusRoutes(): BusArrival
}
package com.bizznizz.boardthebus

import com.bizznizz.boardthebus.model.BusArrival
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val BUS_STOP_CODE = "BusStopCode"
private const val SERVICE_NO = "ServiceNo"

interface BusArrivalService {

    @GET("BusArrivalv2")
    fun getBusArrivalByBus(@Query(BUS_STOP_CODE) busStopCode: String, @Query(SERVICE_NO) serviceNo: String): Single<BusArrival>
}
package com.appcessible.boardthebus

import com.appcessible.boardthebus.model.BusArrival
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.NextBus
import com.google.gson.GsonBuilder
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JsonParserTest {

    @Test
    fun `bus arrival is parsed correctly`() {
        val gson = GsonBuilder().create()
        val mockObj = ClassLoader.getSystemResourceAsStream("busarrivals.json").bufferedReader().use { it.readText() }
        val busArrival = gson.fromJson(mockObj, BusArrival::class.java)

        val nextBus1 = NextBus(
            OriginCode = "52009",
            DestinationCode = "84009",
            EstimatedArrival = "2023-10-01T23:42:01+08:00",
            Latitude = "1.3154563333333333",
            Longitude = "103.90590416666667",
            VisitNumber = "1",
            Load = "SEA",
            Feature = "WAB",
            Type = "SD"
        )
        val firstBusService = BusService(
            ServiceNo = "155",
            Direction = 1,
            Operator = "SBST",
            NextBus = nextBus1,
            NextBus2 = null,
            NextBus3 = null
        )
        val expected = BusArrival("83139", listOf(firstBusService))

        assertEquals(expected.BusStopCode, busArrival.BusStopCode)
        assertEquals(expected.Services!![0], firstBusService)
    }
}

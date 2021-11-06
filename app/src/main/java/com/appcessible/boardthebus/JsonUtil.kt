package com.appcessible.boardthebus

import android.content.Context
import com.google.gson.GsonBuilder
import com.appcessible.boardthebus.model.BusArrival
import javax.inject.Inject

class JsonUtil @Inject constructor(private val context: Context) {

    fun getMockBusArrival(): BusArrival {
        val file = "busarrivals.json"
        val mockObj = context.assets.open(file).bufferedReader().use { it.readText() }
        val gson = GsonBuilder().create()
        return gson.fromJson(mockObj, BusArrival::class.java)
    }
}
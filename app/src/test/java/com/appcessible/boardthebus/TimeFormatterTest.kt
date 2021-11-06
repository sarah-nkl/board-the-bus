package com.appcessible.boardthebus

import org.junit.Test

import org.junit.Assert.*

class TimeFormatterTest {
    @Test
    fun `format estimated arrival time`() {
        val originalResponse = "2021-04-25T17:40:24+08:00"
        val timeFormatter = TimeFormatter()
        assertNotEquals("error", timeFormatter.getEstimatedArrivalInMin(originalResponse))
    }
}

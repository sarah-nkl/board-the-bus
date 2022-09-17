package com.appcessible.boardthebus

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TimeFormatterHelper @Inject constructor() : TimeFormatter {

    override fun getEstimatedArrivalInMin(time: String): String {
        if (time.isEmpty()) return "NA"
        // e.g. 2017-04-29T07:20:24+08:00 -> remove last colon
        val revisedTime = time.substring(0, time.length - 3) +
            time.substring(time.length - 2, time.length)
        val originalFormat = "yyyy-MM-dd'T'hh:mm:ssZ"
        val simpleDateFormat = SimpleDateFormat(originalFormat, Locale.getDefault())
        val date = simpleDateFormat.parse(revisedTime) ?: return "error"
        val currentDate = Date()
        val differenceInMillis = date.time - currentDate.time
        val result = (differenceInMillis / 1000 / 60).coerceAtLeast(0)

        return result.toString()
    }
}
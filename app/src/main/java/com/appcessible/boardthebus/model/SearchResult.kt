package com.appcessible.boardthebus.model

data class SearchResult(
    val busStopCode: String,
    val description: String,
    val label: SearchResultLabel
)

enum class SearchResultLabel(val desc: String) {
    BUS_STOP("Bus Stop"), BUS_SERVICE("Bus Service"), ADDRESS("Address")
}
package com.appcessible.boardthebus.model

data class SearchResult(
    val id: String,
    val name: String,
    val description: String,
    val label: SearchResultLabel
)

enum class SearchResultLabel(val desc: String) {
    ADDRESS("Address"),
    BUS_SERVICE("Bus Service"),
    BUS_STOP("Bus Stop"),
}
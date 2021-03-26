package com.timaklokov.tickets

data class SymbolModel(
    var symbol: String? = null,
    var name: String? = null,
    var price: Double? = null,
    var exchange: String? = null
)
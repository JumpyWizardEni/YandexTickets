package com.timaklokov.tickets.data.net.models

data class ViewHolderModel (
    var companyName: String? = null,
    var symbol: String? = null,
    var currentPrice: Double? = null,
    var priceChange: Double? = null,
    var isFavourite: Boolean = false
)
package com.timaklokov.tickets.data.db.models

import androidx.room.*

@Entity
data class SymbolEntity (
    var companyName: String = "",
    var symbol: String = "",
    var price: Double = 0.0,
    var change: Double = 0.0,
    var isFavourite: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
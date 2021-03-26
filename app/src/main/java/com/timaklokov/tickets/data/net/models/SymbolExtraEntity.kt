package com.timaklokov.tickets.data.net.models

import com.google.gson.annotations.SerializedName

data class SymbolExtraEntity(
    @SerializedName("date") var date : String,
    @SerializedName("open") var open : Double,
    @SerializedName("low") var low : Double,
    @SerializedName("high") var high : Double,
    @SerializedName("close") var close : Double,
    @SerializedName("volume") var volume : Int
)
package com.timaklokov.tickets.data.net

import com.timaklokov.tickets.SymbolModel
import com.timaklokov.tickets.data.net.models.SymbolExtraEntity
import com.timaklokov.tickets.data.net.models.SymbolPrice
import retrofit2.http.*

interface RetrofitServices {
    @GET("stock/list?apikey=a415f7b8e599db8f8e01c13d459cdcf2")
    suspend fun getCountryList(): MutableList<SymbolModel>

    @GET("quote/{symbols}?apikey=a415f7b8e599db8f8e01c13d459cdcf2")
    suspend fun getSymbolData(@Path("symbols") symbols: String): List<SymbolPrice>

    @GET("historical-chart/1min/{symbol}?apikey=a415f7b8e599db8f8e01c13d459cdcf2")
    suspend fun getSymbolExtra(
        @Path("symbol") symbol: String
    ): MutableList<SymbolExtraEntity>
}
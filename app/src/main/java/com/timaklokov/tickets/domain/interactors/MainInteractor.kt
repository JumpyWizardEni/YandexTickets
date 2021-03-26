package com.timaklokov.tickets.domain.interactors

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.timaklokov.tickets.data.db.models.SymbolEntity
import com.timaklokov.tickets.data.db.repositories.SymbolRepository
import com.timaklokov.tickets.data.net.RetrofitClientMarket
import com.timaklokov.tickets.data.net.RetrofitServices
import com.timaklokov.tickets.data.net.models.SymbolExtraEntity
import com.timaklokov.tickets.data.net.models.SymbolPrice


class MainInteractor(val context: Context) {
    val MAX_TRADES = 500
    val repos = SymbolRepository(context)
    var prefs = context.getSharedPreferences("Trades", 0)
    var editor = prefs.edit()
    var retrofitServiceMarket =
        RetrofitClientMarket.getClient("https://financialmodelingprep.com/api/v3/")
            .create(RetrofitServices::class.java)

    suspend fun getStocks(): List<SymbolEntity> {
        var recViewData: MutableList<SymbolEntity> = arrayListOf()

        if (prefs.getBoolean("isFirst", true) == false) {
            recViewData.addAll(repos.getAll())
        } else {
            editor.putBoolean("isFirst", false)
            editor.apply()
            return getNetData(true)

        }
        return recViewData
    }

    suspend fun getSymbols(): ArrayList<String?> {
        var symbols = prefs.getString("Symbols", null)
        var companies = prefs.getString("Companies", null)
        if ((symbols == null) || (companies == null)) {
            symbols = ""
            companies = ""
            var response = retrofitServiceMarket.getCountryList()
            for (i in 0 until MAX_TRADES) {
                if (i == MAX_TRADES - 1) {
                    symbols += response[i].symbol!!
                    companies += response[i].name!!
                } else {
                    symbols += response[i].symbol!! + ","
                    companies += response[i].name!! + ","
                }
            }
            editor.putString("Symbols", symbols)
            editor.putString("Companies", companies)
            editor.apply()
        }
        return arrayListOf(
            prefs.getString("Symbols", "")
            , prefs.getString("Companies", "")
        )

    }

    suspend fun getNetData(isFirst: Boolean): List<SymbolEntity> {
        var recViewData: MutableList<SymbolEntity> = arrayListOf()
        var cs = getSymbols()
        var symbols = cs[0]!!.split(",").toTypedArray()
        var companies = cs[1]!!.split(",").toTypedArray()
        var symbolPrices: List<SymbolPrice> = arrayListOf()
        try {
             symbolPrices = retrofitServiceMarket.getSymbolData(cs[0]!!)
        }
        catch(e: Exception) {
            Toast.makeText(context, "Can't get data now. Check your connection.", Toast.LENGTH_LONG).show()
            return getStocks()
        }
        for (i in 0 until symbolPrices.size) {

            var newHolder = SymbolEntity()
            newHolder.companyName = companies[i]
            newHolder.symbol = symbols[i]
            for(j in 0 until symbolPrices.size) {
                if(symbolPrices[j].symbol == symbols[i]) {
                    newHolder.price = symbolPrices[j].price
                    break
                }
            }
            if (newHolder.price == null || newHolder.price == 0.0) continue
            if (isFirst == false) {
                newHolder.isFavourite = repos.getFavourite(newHolder.symbol)
            }
            newHolder.change = symbolPrices[i].change

            recViewData.add(newHolder)
        }
        repos.setAll(recViewData)
        return recViewData
    }

    suspend fun updateGroupBySymbol(symbol: String, isFav: Boolean) {
        repos.updateGroupBySymbol(symbol, isFav)
    }

    suspend fun getSymbol(id: Long): MutableLiveData<SymbolEntity> {
        val live: MutableLiveData<SymbolEntity> = MutableLiveData()
        live.value = repos.getSymbol(id)
        return live
    }

    suspend fun getSymbolExtra(symbol: String): MutableList<SymbolExtraEntity> {
        var list: MutableList<SymbolExtraEntity> = mutableListOf()
        try {
            list = retrofitServiceMarket.getSymbolExtra(symbol)
        } catch (e: Exception) {
            Toast.makeText(context, "No access to the Internet", Toast.LENGTH_LONG).show()
            return list
        }
        return retrofitServiceMarket.getSymbolExtra(symbol)
    }
}
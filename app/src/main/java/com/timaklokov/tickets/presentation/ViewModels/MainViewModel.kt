package com.timaklokov.tickets.presentation.ViewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timaklokov.tickets.data.db.models.SymbolEntity
import com.timaklokov.tickets.domain.interactors.MainInteractor
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {
    private var stocks: MutableLiveData<List<SymbolEntity>> = MutableLiveData()
    private var value: MutableList<SymbolEntity> = arrayListOf()
    val interactor: MainInteractor = MainInteractor(context)



    init {
        viewModelScope.launch {
            value = interactor.getStocks() as MutableList<SymbolEntity>
            stocks.value = value
        }
    }

    fun getData() = stocks
    fun getStocks() {
        viewModelScope.launch {
            value = interactor.getStocks() as MutableList<SymbolEntity>
            stocks.value = value
        }
    }

    fun updateData() {
        viewModelScope.launch {
            value = interactor.getNetData(false) as MutableList<SymbolEntity>
            stocks.value = value
        }
    }

    fun updateGroupBySymbol(symbol: String, isFav: Boolean) {
        viewModelScope.launch {
            interactor.updateGroupBySymbol(symbol, isFav)
            for (i in 0 until value.size) {
                if (value[i].symbol == symbol) {
                    stocks.value!![i].isFavourite = isFav
                    value = stocks.value as MutableList<SymbolEntity>
                    stocks.value = value
                    break
                }
            }

        }
    }
}
package com.timaklokov.tickets.presentation.ViewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timaklokov.tickets.data.net.models.SymbolExtraEntity
import com.timaklokov.tickets.domain.interactors.MainInteractor
import kotlinx.coroutines.launch

class SymbolExtraViewModel(context: Context, symbolIn: String) : ViewModel() {
    private var symbol: MutableLiveData<List<SymbolExtraEntity>> = MutableLiveData()
    private var symbolString: String = symbolIn
    val interactor: MainInteractor = MainInteractor(context)

    init {
        viewModelScope.launch {
            symbol.value = interactor.getSymbolExtra(symbolString)
        }
    }

    fun getData() = symbol
    fun getSymbolExtra() {
        viewModelScope.launch {
            symbol.value = interactor.getSymbolExtra(symbolString)
        }
    }
}
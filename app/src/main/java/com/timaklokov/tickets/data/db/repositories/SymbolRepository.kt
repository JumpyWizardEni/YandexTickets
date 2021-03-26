package com.timaklokov.tickets.data.db.repositories

import android.content.Context
import com.timaklokov.tickets.data.db.SymbolDAO
import com.timaklokov.tickets.data.db.SymbolDB
import com.timaklokov.tickets.data.db.models.SymbolEntity

class SymbolRepository(context: Context) {
    private var db = SymbolDB.getInstance(context)
    private var dao: SymbolDAO = db.groupDao()

    suspend fun getAll(): List<SymbolEntity> {
        return dao.loadAll()
    }

    suspend fun setAll(list: List<SymbolEntity>) {
        dao.clean()
        dao.insertAll(list)
    }

    suspend fun updateGroupBySymbol(symbol: String, isFav: Boolean) {
        dao.updateGroupBySymbol(symbol, isFav)
    }

    suspend fun getFavourite(symbol: String): Boolean {
        return dao.getFavourite(symbol).isFavourite
    }

    suspend fun getSymbol(id: Long): SymbolEntity {
        return dao.getSymbol(id)
    }
}
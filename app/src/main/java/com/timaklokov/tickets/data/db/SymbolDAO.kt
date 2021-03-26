package com.timaklokov.tickets.data.db

import androidx.room.*
import com.timaklokov.tickets.data.db.models.SymbolEntity


@Dao
interface SymbolDAO {
    @Query("SELECT * FROM SymbolEntity")
    suspend fun loadAll(): List<SymbolEntity>

    @Query("SELECT * FROM SymbolEntity WHERE id =:id")
    suspend fun getSymbol(id: Long): SymbolEntity

    @Query("UPDATE SymbolEntity SET isFavourite =:isFavourite WHERE symbol =:symbol")
    suspend fun updateGroupBySymbol(symbol: String, isFavourite: Boolean)

    @Query("SELECT * FROM SymbolEntity WHERE symbol =:symbol")
    suspend fun getFavourite(symbol: String): SymbolEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(symbols: List<SymbolEntity>)

    @Query("DELETE FROM SymbolEntity")
    suspend fun clean()
}
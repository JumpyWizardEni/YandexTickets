package com.timaklokov.tickets.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.timaklokov.tickets.data.db.models.SymbolEntity

@Database(entities = arrayOf(SymbolEntity::class), version = 3)
abstract class SymbolDB: RoomDatabase() {
    abstract fun groupDao(): SymbolDAO
    companion object{
        private var instance: SymbolDB? = null
        fun getInstance(context: Context): SymbolDB {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    SymbolDB::class.java, "Symbols").fallbackToDestructiveMigration().build()
            }
            return instance as SymbolDB
        }
    }
}
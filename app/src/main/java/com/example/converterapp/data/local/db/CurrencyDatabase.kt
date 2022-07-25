package com.example.converterapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.converterapp.model.db.CurrencySaved

@Database(entities = [CurrencySaved::class], version = 2)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyDao
}
package com.example.converterapp.data.local.db

import androidx.room.*
import com.example.converterapp.model.db.CurrencySaved
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrency(currency: CurrencySaved)

    @Delete
    fun deleteCurrency(currency: CurrencySaved)

    @Query("SELECT * FROM savedCurrencies")
    fun getAllCurrency(): Flow<List<CurrencySaved>>

    @Query("SELECT * FROM savedCurrencies WHERE fromCurrencyName LIKE :fromRate AND toCurrencyName LIKE :toRate")
    fun getCurrency(fromRate: String, toRate: String): List<CurrencySaved>
}
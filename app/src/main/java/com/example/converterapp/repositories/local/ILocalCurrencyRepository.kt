package com.example.converterapp.repositories.local

import com.example.converterapp.model.db.CurrencySaved
import kotlinx.coroutines.flow.Flow

interface ILocalCurrencyRepository {

    suspend fun getAllSavedCurrency(): Flow<List<CurrencySaved>>

    suspend fun saveCurrency(currencySaved: CurrencySaved)

    suspend fun deleteCurrency(currencySaved: CurrencySaved)

    suspend fun getCurrency(fromRate: String, toRate: String): CurrencySaved
}
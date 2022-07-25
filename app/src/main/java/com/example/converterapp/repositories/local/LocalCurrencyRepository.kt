package com.example.converterapp.repositories.local

import com.example.converterapp.data.local.db.CurrencyDao
import com.example.converterapp.model.db.CurrencySaved
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalCurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao
) : ILocalCurrencyRepository {

    override suspend fun getAllSavedCurrency(): Flow<List<CurrencySaved>> = withContext(Dispatchers.IO) {
        currencyDao.getAllCurrency()
    }

    override suspend fun saveCurrency(currencySaved: CurrencySaved) {
        withContext(Dispatchers.IO) {
            currencyDao.saveCurrency(currencySaved)
        }
    }

    override suspend fun deleteCurrency(currencySaved: CurrencySaved) {
        withContext(Dispatchers.IO) {
            currencyDao.deleteCurrency(currencySaved)
        }
    }

    override suspend fun getCurrency(fromRate: String, toRate: String): CurrencySaved = withContext(Dispatchers.IO) {
        currencyDao.getCurrency(fromRate, toRate).first()
    }
}
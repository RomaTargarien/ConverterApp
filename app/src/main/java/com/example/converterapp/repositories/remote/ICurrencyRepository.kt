package com.example.converterapp.repositories.remote

import com.example.converterapp.model.remote.Currency
import com.example.converterapp.util.Resource

interface ICurrencyRepository {
    suspend fun getCurrency(currencyName: String): Resource<Currency>
}
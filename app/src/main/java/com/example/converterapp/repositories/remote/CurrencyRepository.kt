package com.example.converterapp.repositories.remote

import com.example.converterapp.data.remote.CurrencyApi
import com.example.converterapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyApi: CurrencyApi
) : ICurrencyRepository {

    override suspend fun getCurrency(currencyName: String) = withContext(Dispatchers.IO) {
        try {
            val result = currencyApi.getCurrency(currencyName)
            Resource.Success(result)
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage)
        }
    }
}
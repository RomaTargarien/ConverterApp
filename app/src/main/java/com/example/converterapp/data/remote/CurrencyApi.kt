package com.example.converterapp.data.remote

import com.example.converterapp.model.remote.Currency
import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest?base=USD")
    suspend fun getCurrency(): Currency
}
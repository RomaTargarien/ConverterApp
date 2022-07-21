package com.example.converterapp.data.remote

import com.example.converterapp.model.remote.Currency
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getCurrency(@Query ("base") currencyName: String = "USD"): Currency
}
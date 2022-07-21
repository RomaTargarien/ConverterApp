package com.example.converterapp.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedCurrencies")
data class CurrencySaved(
    var fromCurrencyName: String = "",
    var toCurrencyName: String = "",
    var value: Double = 0.0
) {
    @PrimaryKey(autoGenerate = true)
    var currencyPrimaryKey: Int = 0
}
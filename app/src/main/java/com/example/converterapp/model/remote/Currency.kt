package com.example.converterapp.model.remote

import com.example.converterapp.model.ui.Rate
import com.example.converterapp.util.ext.createFlagUrl

data class Currency(
    val base: String,
    val date: String,
    val motd: Motd,
    val rates: Map<String, Double>,
    val success: Boolean
) {
    fun getRates() = rates.entries.map {
        Rate(
            name = it.key,
            value = it.value,
            iconUrl = it.key.createFlagUrl()
        )
    }
}
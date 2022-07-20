package com.example.converterapp.model.remote

data class Currency(
    val base: String,
    val date: String,
    val motd: Motd,
    val rates: Map<String, Double>,
    val success: Boolean
)
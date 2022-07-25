package com.example.converterapp.model.ui

data class Rate(
    val name: String,
    val value: Double,
    val iconUrl: String,
    var isSaved: Boolean = false
)
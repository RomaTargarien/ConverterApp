package com.example.converterapp.util.ext

fun String.createFlagUrl() = "https://www.countryflagicons.com/FLAT/64/${this.subSequence(0..1)}.png"
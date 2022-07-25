package com.example.converterapp.ui.screens.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.data.local.shared.IUserPreferences
import com.example.converterapp.model.db.CurrencySaved
import com.example.converterapp.model.ui.Rate
import com.example.converterapp.repositories.local.ILocalCurrencyRepository
import com.example.converterapp.util.ext.createFlagUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularCurrencyViewModel @Inject constructor(
    private val userPreferences: IUserPreferences,
    private val localCurrencyRepository: ILocalCurrencyRepository
) : ViewModel() {


    fun onSaveCurrencyClicked(rate: Rate) {
        viewModelScope.launch {
            val currencySaved = CurrencySaved(
                fromCurrencyName = userPreferences.lastSelectedCurrency,
                fromCurrencyFlagUrl = userPreferences.lastSelectedCurrency.createFlagUrl(),
                toCurrencyName = rate.name,
                toCurrencyFlagUrl = rate.name.createFlagUrl(),
                value = rate.value
            )
            localCurrencyRepository.saveCurrency(currencySaved)
        }
    }

    fun onDeleteCurrencyClicked(rate: Rate) {
        viewModelScope.launch {
            val currencySaved = localCurrencyRepository.getCurrency(userPreferences.lastSelectedCurrency, rate.name)
            localCurrencyRepository.deleteCurrency(currencySaved)
        }
    }

}
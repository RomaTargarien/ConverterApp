package com.example.converterapp.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.model.db.CurrencySaved
import com.example.converterapp.repositories.local.ILocalCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val localCurrencyRepository: ILocalCurrencyRepository
) : ViewModel() {

    fun deleteCurrency(saved: CurrencySaved) {
        viewModelScope.launch {
            localCurrencyRepository.deleteCurrency(saved)
        }
    }
}
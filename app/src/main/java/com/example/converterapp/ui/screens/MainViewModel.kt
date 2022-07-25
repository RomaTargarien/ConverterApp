package com.example.converterapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.data.local.shared.IUserPreferences
import com.example.converterapp.model.db.CurrencySaved
import com.example.converterapp.model.ui.Rate
import com.example.converterapp.repositories.local.ILocalCurrencyRepository
import com.example.converterapp.repositories.remote.ICurrencyRepository
import com.example.converterapp.ui.Screens
import com.example.converterapp.util.Constants
import com.example.converterapp.util.RatesListCreator
import com.example.converterapp.util.Resource
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: Router,
    private val remoteCurrencyRepository: ICurrencyRepository,
    private val userPreferences: IUserPreferences,
    private val localCurrencyRepository: ILocalCurrencyRepository
) : ViewModel() {

    private val _remoteCurrencyFlow: MutableStateFlow<Resource<List<Rate>>> = MutableStateFlow(Resource.Loading())
    val remoteCurrencyFlow: StateFlow<Resource<List<Rate>>> = _remoteCurrencyFlow

    private val _localCurrencyFlow: MutableStateFlow<List<CurrencySaved>> = MutableStateFlow(emptyList())
    val localCurrencyFlow: StateFlow<List<CurrencySaved>> = _localCurrencyFlow

    private val sortedOptionFlow: MutableStateFlow<String> = MutableStateFlow(userPreferences.sortedOption)
    private val remoteRatesList: MutableStateFlow<List<Rate>> = MutableStateFlow(emptyList())

    private val _currentCurrencyFlow: MutableStateFlow<String> =
        MutableStateFlow(userPreferences.lastSelectedCurrency.ifEmpty { Constants.DEFAULT_CURRENCY })
    val currentCurrencyFlow: StateFlow<String> = _currentCurrencyFlow

    private val _flagsFlow: MutableStateFlow<List<Rate>> = MutableStateFlow(emptyList())
    val flagsFlow: StateFlow<List<Rate>> = _flagsFlow
    private var wasFlagsSubmitted: Boolean = false

    init {
        observeData()
        getLocalCurrency()
        getRemoteCurrency(userPreferences.lastSelectedCurrency.ifEmpty { Constants.DEFAULT_CURRENCY })
    }

    fun getRemoteCurrency(currencyName: String) {
        userPreferences.lastSelectedCurrency = currencyName
        viewModelScope.launch {
            _remoteCurrencyFlow.emit(Resource.Loading())
            _currentCurrencyFlow.emit(currencyName)
            remoteCurrencyRepository.getCurrency(currencyName).apply {
                when (this) {
                    is Resource.Error -> {
                        _remoteCurrencyFlow.emit(Resource.Error(this.message))
                    }
                    is Resource.Success -> {
                        this.data?.getRates()?.let { rates ->
                            if (!wasFlagsSubmitted) {
                                _flagsFlow.emit(rates)
                                wasFlagsSubmitted = true
                            }
                            remoteRatesList.emit(rates)
                        }
                    }
                }
            }
        }
    }

    fun retry() {
        getRemoteCurrency(userPreferences.lastSelectedCurrency)
    }

    fun onReceiveSortedEvent(sortName: String) {
        viewModelScope.launch { sortedOptionFlow.emit(sortName) }
    }

    fun onSortCurrencyButtonClicked() {
        router.navigateTo(Screens.sortScreen())
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(remoteRatesList, localCurrencyFlow, sortedOptionFlow) { remote, local, sortOption ->
                RatesListCreator.processData(remote, local, sortOption, userPreferences.lastSelectedCurrency)
            }.collect {
                if (it.isNotEmpty()) {
                    _remoteCurrencyFlow.emit(Resource.Success(it))
                }
            }
        }
    }

    private fun getLocalCurrency() {
        viewModelScope.launch {
            localCurrencyRepository.getAllSavedCurrency().collect { currencySavedList ->
                _localCurrencyFlow.emit(currencySavedList)
            }
        }
    }
}
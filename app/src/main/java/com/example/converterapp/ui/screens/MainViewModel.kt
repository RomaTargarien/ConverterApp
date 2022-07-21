package com.example.converterapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.data.local.shared.IUserPreferences
import com.example.converterapp.model.ui.Rate
import com.example.converterapp.repositories.remote.ICurrencyRepository
import com.example.converterapp.ui.Screens
import com.example.converterapp.util.Resource
import com.example.converterapp.util.Sorts
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: Router,
    private val remoteCurrencyRepository: ICurrencyRepository,
    private val userPreferences: IUserPreferences
) : ViewModel() {

    private val _remoteCurrencyFlow: MutableStateFlow<Resource<List<Rate>>> = MutableStateFlow(Resource.Loading())
    val remoteCurrencyFlow: StateFlow<Resource<List<Rate>>> = _remoteCurrencyFlow

    private val sortedOptionFlow: MutableStateFlow<String> = MutableStateFlow(userPreferences.sortedOption)
    val remoteRatesList: MutableStateFlow<List<Rate>> = MutableStateFlow(emptyList())


    init {
        viewModelScope.launch {
            combine(remoteRatesList, sortedOptionFlow) { list, _ ->
                processData()
            }
                .collect {
                    Log.d("TAG",it.size.toString())
                    _remoteCurrencyFlow.emit(Resource.Success(it))
                }
        }
        getRemoteCurrency(userPreferences.lastSelectedCurrency.ifEmpty { "USD" })
    }

    fun getRemoteCurrency(currencyName: String) {
        userPreferences.lastSelectedCurrency = currencyName
        viewModelScope.launch {
            _remoteCurrencyFlow.emit(Resource.Loading())
            remoteCurrencyRepository.getCurrency(currencyName).apply {
                this.data?.rates?.entries?.map {
                    Rate(it.key, it.value)
                }?.let {
                    remoteRatesList.emit(it)
                }
            }
        }
    }

    fun onReceiveSortedEvent(sortName: String) {
        viewModelScope.launch { sortedOptionFlow.emit(sortName) }
    }

    fun onSortCurrencyButtonClicked() {
        router.navigateTo(Screens.sortScreen())
    }

    private fun processData(): List<Rate> {
        val copiedList = mutableListOf<Rate>()
        remoteRatesList.value.forEach {
            copiedList.add(it)
        }
        return when (sortedOptionFlow.value) {
            Sorts.SORT_BY_NAME_ASCENDING.name -> copiedList.sortedBy { it.name }
            Sorts.SORT_BY_NAME_DESCENDING.name -> copiedList.sortedBy { it.name }.reversed()
            Sorts.SORT_BY_VALUE_ASCENDING.name -> copiedList.sortedBy { it.value }
            Sorts.SORT_BY_VALUE_DESCENDING.name -> copiedList.sortedBy { it.value }.reversed()
            else -> copiedList
        }
    }
}
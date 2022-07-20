package com.example.converterapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.model.ui.Rate
import com.example.converterapp.repositories.remote.ICurrencyRepository
import com.example.converterapp.ui.Screens
import com.example.converterapp.util.Resource
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: Router,
    private val remoteCurrencyRepository: ICurrencyRepository
) : ViewModel() {

    private val _remoteCurrencyFlow: MutableStateFlow<Resource<List<Rate>>> = MutableStateFlow(Resource.Loading())
    val remoteCurrencyFlow: StateFlow<Resource<List<Rate>>> = _remoteCurrencyFlow

    init {
        getRemoteCurrency()
    }

    private fun getRemoteCurrency() {
        viewModelScope.launch {
            _remoteCurrencyFlow.emit(Resource.Loading())
            remoteCurrencyRepository.getCurrency().apply {
                this.data?.rates?.entries?.map {
                    Rate(it.key, it.value)
                }?.let {
                    _remoteCurrencyFlow.emit(Resource.Success(it))
                }
            }
        }
    }

    fun onReceiveSortedEvent() {

    }

    fun onSortCurrencyButtonClicked() {
        router.navigateTo(Screens.sortScreen())
    }

}
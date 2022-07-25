package com.example.converterapp.ui.screens.popular.sort

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.converterapp.data.local.shared.IUserPreferences
import com.example.converterapp.model.ui.SortOption
import com.example.converterapp.ui.screens.MainFragment
import com.example.converterapp.util.Sorts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortCurrencyViewModel @Inject constructor(
    private val localBroadcastManager: LocalBroadcastManager,
    private val userPreferences: IUserPreferences
) : ViewModel() {

    private val _sorts: MutableStateFlow<List<SortOption>> = MutableStateFlow(createSortList(userPreferences.sortedOption))
    val sorts: StateFlow<List<SortOption>> = _sorts


    fun onCheckBoxClicked(sortOption: SortOption) {
        val sortName = if (sortOption.isChecked) Sorts.SORT_BY_DEFAULT.name else sortOption.sortOption.name
        viewModelScope.launch {
            _sorts.emit(createSortList(sortName))
            userPreferences.sortedOption = sortName
            localBroadcastManager.sendBroadcast(Intent(MainFragment.SORT_UPDATE_ACTION).apply {
                putExtra(MainFragment.SORT_UPDATE_DATA, sortName)
            })
        }
    }

    private fun createSortList(name: String): List<SortOption> {
        val defaultList = mutableListOf<SortOption>()
        Sorts.values().forEach { sort ->
            if (sort.name != Sorts.SORT_BY_DEFAULT.name) {
                defaultList.add(SortOption(sort, sort.name == name))
            }
        }
        return defaultList
    }
}
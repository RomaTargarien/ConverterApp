package com.example.converterapp.util

import com.example.converterapp.model.db.CurrencySaved
import com.example.converterapp.model.ui.Rate
import com.example.converterapp.model.ui.SortOption

class DataHelper {

    companion object {
        fun combineRemoteAndLocalData(
            remoteListRate: List<Rate>,
            localListRate: List<CurrencySaved>,
            sortOption: String,
            lastSelectedRate: String
        ): List<Rate> {
            val copiedList = mutableListOf<Rate>()
            remoteListRate.forEach { rate ->
                val rateCopy = rate.copy()
                val saved = localListRate.find { it.fromCurrencyName == lastSelectedRate && it.toCurrencyName == rateCopy.name }
                rateCopy.isSaved = saved != null
                copiedList.add(rateCopy)
            }
            return when (sortOption) {
                Sorts.SORT_BY_NAME_ASCENDING.name -> copiedList.sortedBy { it.name }
                Sorts.SORT_BY_NAME_DESCENDING.name -> copiedList.sortedBy { it.name }.reversed()
                Sorts.SORT_BY_VALUE_ASCENDING.name -> copiedList.sortedBy { it.value }
                Sorts.SORT_BY_VALUE_DESCENDING.name -> copiedList.sortedBy { it.value }.reversed()
                else -> copiedList
            }
        }

        fun createSortList(name: String): List<SortOption> {
            val defaultList = mutableListOf<SortOption>()
            Sorts.values().forEach { sort ->
                if (sort.name != Sorts.SORT_BY_DEFAULT.name) {
                    defaultList.add(SortOption(sort, sort.name == name))
                }
            }
            return defaultList
        }
    }
}
package com.example.converterapp.util

import com.example.converterapp.model.db.CurrencySaved
import com.example.converterapp.model.ui.Rate

enum class Sorts(val uiName: String) {
    SORT_BY_NAME_ASCENDING("Sort by name ascending"),
    SORT_BY_NAME_DESCENDING("Sort by name descending"),
    SORT_BY_VALUE_ASCENDING("Sort by value ascending"),
    SORT_BY_VALUE_DESCENDING("Sort by value descending"),
    SORT_BY_DEFAULT("")
}
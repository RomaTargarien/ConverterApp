package com.example.converterapp.model.ui

import com.example.converterapp.util.Sorts

data class SortOption(
    var sortOption: Sorts = Sorts.SORT_BY_DEFAULT,
    var isChecked: Boolean = false
)

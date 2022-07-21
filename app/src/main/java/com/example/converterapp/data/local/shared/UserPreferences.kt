package com.example.converterapp.data.local.shared

import android.content.Context
import android.content.SharedPreferences
import com.example.converterapp.util.Constants
import com.example.converterapp.util.Sorts
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) : IUserPreferences {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var sortedOption: String = Sorts.SORT_BY_DEFAULT.name
        get() = sharedPref.getString(KEY_SORTED_OPTION, Sorts.SORT_BY_DEFAULT.name) ?: Sorts.SORT_BY_DEFAULT.name
        set(value) {
            val edit = sharedPref.edit()
            edit.putString(KEY_SORTED_OPTION, value)
            edit.apply()
            field = value
        }

    override var lastSelectedCurrency: String = Constants.DEFAULT_CURRENCY
        get() = sharedPref.getString(KEY_LAST_CURRENCY, Constants.DEFAULT_CURRENCY) ?: Constants.DEFAULT_CURRENCY
        set(value) {
            val edit = sharedPref.edit()
            edit.putString(KEY_LAST_CURRENCY, value)
            edit.apply()
            field = value
        }

    companion object {
        private const val PREFS_NAME = "prefs"
        private const val KEY_SORTED_OPTION = "sorted.option"
        private const val KEY_LAST_CURRENCY = "last.currency"
    }
}
package com.example.converterapp.ui.screens.popular.sort

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.converterapp.ui.screens.MainFragment
import com.example.converterapp.ui.screens.popular.PopularCurrencyFragment
import com.example.converterapp.util.Sorts
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class SortCurrencyViewModel @Inject constructor(
    private val localBroadcastManager: LocalBroadcastManager
) : ViewModel() {

    fun onCheckBoxClicked(value: Sorts) {
        localBroadcastManager.sendBroadcast(Intent(MainFragment.SORT_UPDATE_ACTION).apply {
            putExtra(
                MainFragment.SORT_UPDATE_DATA,
                value.name
            )
        })
    }
}
package com.example.converterapp.ui

import com.example.converterapp.ui.screens.MainFragment
import com.example.converterapp.ui.screens.popular.sort.SortCurrencyFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun mainScreen() = FragmentScreen {
        MainFragment()
    }

    fun sortScreen() = FragmentScreen {
        SortCurrencyFragment()
    }
}
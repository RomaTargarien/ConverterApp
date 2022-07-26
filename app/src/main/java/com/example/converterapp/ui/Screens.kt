package com.example.converterapp.ui

import com.example.converterapp.ui.screens.MainFragment
import com.example.converterapp.ui.screens.popular.sort.SortFragment
import com.example.converterapp.ui.screens.splash.SplashFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun mainScreen() = FragmentScreen {
        MainFragment()
    }

    fun sortScreen() = FragmentScreen {
        SortFragment()
    }

    fun splashScreen() = FragmentScreen {
        SplashFragment()
    }
}
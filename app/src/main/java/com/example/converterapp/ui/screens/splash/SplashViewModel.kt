package com.example.converterapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.ui.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    fun onSplashScreenCreated() {
        viewModelScope.launch {
            delay(DEFAULT_SPLASH_TIME)
            router.newRootScreen(Screens.mainScreen())
        }
    }

    companion object {
        private const val DEFAULT_SPLASH_TIME = 3000L
    }
}
package com.example.converterapp.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.converterapp.R
import com.example.converterapp.ui.screens.MainFragment
import com.github.terrakok.cicerone.androidx.AppNavigator

class BaseNavigator(activity: FragmentActivity, containerId: Int) : AppNavigator(activity, containerId) {
    override fun setupFragmentTransaction(fragmentTransaction: FragmentTransaction, currentFragment: Fragment?, nextFragment: Fragment?) {
        when {
            currentFragment == null && nextFragment is MainFragment -> {
            }
            else -> {
                fragmentTransaction.setCustomAnimations(
                    R.anim.enter_anim,
                    R.anim.exit_anim,
                    R.anim.pop_enter_anim,
                    R.anim.pop_exit_anim
                )
            }
        }
    }
}
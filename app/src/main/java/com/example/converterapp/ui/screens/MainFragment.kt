package com.example.converterapp.ui.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.converterapp.R
import com.example.converterapp.databinding.FragmentMainBinding
import com.example.converterapp.ui.screens.favourites.FavouritesCurrencyFragment
import com.example.converterapp.ui.screens.popular.PopularCurrencyFragment
import com.example.converterapp.util.Sorts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val sorterCurrencyRatesUpdater = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getStringExtra(SORT_UPDATE_DATA)?.also {
                Log.d("TAG",it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            sorterCurrencyRatesUpdater, IntentFilter(SORT_UPDATE_ACTION)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            getFragmentForTabId(item.itemId)?.run {
                replaceFragment(this); true
            } ?: false
        }
        if (savedInstanceState == null && childFragmentManager.fragments.size == 0) {
            binding.bottomNavigationView.selectedItemId = R.id.mi_home
        }


    }

    private fun getFragmentForTabId(tabId: Int): Fragment? {
        return when (tabId) {
            R.id.mi_home -> PopularCurrencyFragment()
            R.id.mi_favourites -> FavouritesCurrencyFragment()
            else -> null
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.tab_content, fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(sorterCurrencyRatesUpdater)
    }

    companion object {
        const val SORT_UPDATE_ACTION = "sort.update.action"
        const val SORT_UPDATE_DATA = "sort.update.data"
    }
}
package com.example.converterapp.ui.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.ui.decorators.VerticalItemVerticalDecoration
import com.example.converterapp.R
import com.example.converterapp.databinding.FragmentMainBinding
import com.example.converterapp.ui.screens.favourites.FavouritesCurrencyFragment
import com.example.converterapp.ui.screens.popular.PopularCurrencyFragment
import com.example.converterapp.util.ext.createFlagUrl
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val sorterCurrencyRatesUpdater = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getStringExtra(SORT_UPDATE_DATA)?.also {
                viewModel.onReceiveSortedEvent(it)
            }
        }
    }
    private lateinit var currencyChooserAdapter: CurrencyChooserAdapter

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
        setUpBottomNavigation(savedInstanceState)
        setUpRecyclerView()
        observeRateChooserList()
        observeCurrentCurrency()
    }

    private fun setUpBottomNavigation(savedInstanceState: Bundle?) {
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            getFragmentForTabId(item.itemId)?.run {
                changeTitleText(this)
                replaceFragment(this); true
            } ?: false
        }
        if (savedInstanceState == null && childFragmentManager.fragments.size == 0) {
            binding.bottomNavigationView.selectedItemId = R.id.mi_home
        }
    }

    private fun setUpRecyclerView() {
        currencyChooserAdapter = CurrencyChooserAdapter()
        currencyChooserAdapter.setOnRateClickedListener { rate ->
            viewModel.getRemoteCurrency(rate.name)
        }
        binding.rvFlags.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(VerticalItemVerticalDecoration(10, 10))
            adapter = currencyChooserAdapter
        }
    }

    private fun observeRateChooserList() {
        lifecycleScope.launch {
            viewModel.flagsFlow.collect {
                currencyChooserAdapter.submitList(it)
            }
        }
    }

    private fun observeCurrentCurrency() {
        lifecycleScope.launch {
            viewModel.currentCurrencyFlow.collect {
                Picasso.get().load(it.createFlagUrl())
                    .error(R.drawable.ic_icon_pirate_flag)
                    .into(binding.ivCurrentRateFlag)
                binding.tvCurrentRate.text = it
            }
        }
    }

    private fun changeTitleText(fragment: Fragment) {
        if (fragment is PopularCurrencyFragment) {
            TransitionManager.beginDelayedTransition(binding.tabContainer)
            binding.tvRates.text = resources.getString(R.string.my_rates)
        }
        if (fragment is FavouritesCurrencyFragment) {
            TransitionManager.beginDelayedTransition(binding.tabContainer)
            binding.tvRates.text = resources.getString(R.string.saved_rates)
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
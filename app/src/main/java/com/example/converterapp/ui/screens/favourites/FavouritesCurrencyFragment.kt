package com.example.converterapp.ui.screens.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.ui.decorators.HorizontalItemDecoration
import com.example.converterapp.databinding.FragmentFavouritesCurrencyBinding
import com.example.converterapp.ui.screens.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesCurrencyFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesCurrencyBinding
    private val viewModel: FavouritesCurrencyViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var currencyFavouritesAdapter: CurrencyFavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavouritesCurrencyBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeSavedCurrency()
    }

    private fun observeSavedCurrency() {
        lifecycleScope.launch {
            mainViewModel.localCurrencyFlow.collect {
                currencyFavouritesAdapter.submitList(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        currencyFavouritesAdapter = CurrencyFavouritesAdapter()
        currencyFavouritesAdapter.setOnDeleteCurrencyListener {
            viewModel.deleteCurrency(it)
        }
        binding.rvFavourites.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(HorizontalItemDecoration(10))
            adapter = currencyFavouritesAdapter
        }
    }
}
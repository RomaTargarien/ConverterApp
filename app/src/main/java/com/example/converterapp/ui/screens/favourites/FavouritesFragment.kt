package com.example.converterapp.ui.screens.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.databinding.FragmentFavouritesBinding
import com.example.converterapp.ui.decorators.HorizontalItemDecoration
import com.example.converterapp.ui.screens.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private val viewModel: FavouritesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var currencyFavouritesAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavouritesBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeSavedCurrency()
    }

    private fun observeSavedCurrency() {
        lifecycleScope.launch {
            mainViewModel.localCurrencyFlow.collect {
                binding.lottieEmptyResult.isVisible = it.isEmpty()
                currencyFavouritesAdapter.submitList(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        currencyFavouritesAdapter = FavouritesAdapter()
        currencyFavouritesAdapter.setOnDeleteCurrencyListener {
            viewModel.deleteCurrency(it)
        }
        binding.rvFavourites.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(HorizontalItemDecoration(20))
            adapter = currencyFavouritesAdapter
        }
    }
}
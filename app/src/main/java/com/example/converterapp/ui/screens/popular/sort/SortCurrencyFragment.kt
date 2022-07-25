package com.example.converterapp.ui.screens.popular.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.databinding.FragmentSortCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SortCurrencyFragment : Fragment() {

    private lateinit var binding: FragmentSortCurrencyBinding
    private lateinit var sortAdapter: SortAdapter
    private val viewModel: SortCurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSortCurrencyBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setUpRecyclerView()
        observeSavedCurrency()
    }

    private fun observeSavedCurrency() {
        lifecycleScope.launch {
            viewModel.sorts.collect { list ->
                sortAdapter.submitList(list)
            }
        }
    }

    private fun setUpRecyclerView() {
        sortAdapter = SortAdapter()
        sortAdapter.setOnSortOptionListener {
            viewModel.onCheckBoxClicked(it)
        }
        binding.rvSorts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = sortAdapter
        }
    }
}
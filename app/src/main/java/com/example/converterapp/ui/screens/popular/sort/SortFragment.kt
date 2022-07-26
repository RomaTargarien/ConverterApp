package com.example.converterapp.ui.screens.popular.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.databinding.FragmentSortBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SortFragment : Fragment() {

    private lateinit var binding: FragmentSortBinding
    private lateinit var sortAdapter: SortAdapter
    private val viewModel: SortViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSortBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
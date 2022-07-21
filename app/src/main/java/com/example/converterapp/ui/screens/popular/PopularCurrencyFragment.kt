package com.example.converterapp.ui.screens.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.databinding.FragmentPopularCurrencyBinding
import com.example.converterapp.ui.screens.CurrencyAdapter
import com.example.converterapp.ui.screens.MainViewModel
import com.example.converterapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularCurrencyFragment : Fragment() {

    private lateinit var binding: FragmentPopularCurrencyBinding
    private lateinit var currencyAdapter: CurrencyAdapter
    private val viewModel: PopularCurrencyViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPopularCurrencyBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        lifecycleScope.launch {
            mainViewModel.remoteCurrencyFlow.collect { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        result.data?.let { list ->
                            currencyAdapter.submitList(list)
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }


    private fun setUpRecyclerView() {
        currencyAdapter = CurrencyAdapter()
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = currencyAdapter
        }
    }
}
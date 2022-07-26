package com.example.converterapp.ui.screens.popular

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
import com.example.converterapp.databinding.FragmentHomeBinding
import com.example.converterapp.ui.decorators.HorizontalItemDecoration
import com.example.converterapp.ui.screens.CurrencyAdapter
import com.example.converterapp.ui.screens.MainViewModel
import com.example.converterapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var currencyAdapter: CurrencyAdapter
    private val viewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainViewModel = mainViewModel
        setUpRecyclerView()
        observeRemoteCurrencyData()
    }

    private fun observeRemoteCurrencyData() {
        lifecycleScope.launch {
            mainViewModel.remoteCurrencyFlow.collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.pbPopular.isVisible = true
                        binding.bnRetry.isVisible = false
                        binding.rvPopular.isVisible = false
                    }
                    is Resource.Success -> {
                        binding.pbPopular.isVisible = false
                        binding.bnRetry.isVisible = false
                        result.data?.let { list ->
                            currencyAdapter.submitList(list)
                            binding.rvPopular.isVisible = true
                        }
                    }
                    is Resource.Error -> {
                        binding.rvPopular.isVisible = false
                        binding.pbPopular.isVisible = false
                        binding.bnRetry.isVisible = true
                    }
                }
            }
        }
    }


    private fun setUpRecyclerView() {
        currencyAdapter = CurrencyAdapter()
        currencyAdapter.setOnSaveCurrencyListener {
            viewModel.onSaveCurrencyClicked(it)
        }
        currencyAdapter.setOnDeleteCurrencyClicked {
            viewModel.onDeleteCurrencyClicked(it)
        }
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            addItemDecoration(HorizontalItemDecoration(10))
            adapter = currencyAdapter
        }
    }
}
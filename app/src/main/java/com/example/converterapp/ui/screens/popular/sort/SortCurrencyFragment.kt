package com.example.converterapp.ui.screens.popular.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.converterapp.databinding.FragmentSortCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortCurrencyFragment : Fragment() {

    private lateinit var binding: FragmentSortCurrencyBinding
    private val viewModel: SortCurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSortCurrencyBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}
package com.example.converterapp.ui.screens


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.databinding.ItemRateBinding
import com.example.converterapp.model.ui.Rate

class CurrencyAdapter : ListAdapter<Rate, CurrencyAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRateBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ViewHolder(val binding: ItemRateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: Rate) {
            binding.rate = rate
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Rate>() {
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem == newItem
    }
}
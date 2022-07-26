package com.example.converterapp.ui.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.databinding.ItemRateChooseBinding
import com.example.converterapp.model.ui.Rate

class CurrencyChooserAdapter : ListAdapter<Rate, CurrencyChooserAdapter.ViewHolder>(DiffCallback()) {

    private var onRateClicked: ((Rate) -> Unit)? = null
    fun setOnRateClickedListener(listener: (Rate) -> Unit) {
        onRateClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRateChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(val binding: ItemRateChooseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: Rate) {
            binding.rate = rate
            binding.container.setOnClickListener {
                onRateClicked?.invoke(rate)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Rate>() {
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem == newItem
    }
}
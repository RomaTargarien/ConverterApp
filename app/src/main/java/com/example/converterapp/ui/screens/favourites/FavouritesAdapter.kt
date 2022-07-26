package com.example.converterapp.ui.screens.favourites


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.databinding.ItemRateSavedBinding
import com.example.converterapp.model.db.CurrencySaved

class FavouritesAdapter : ListAdapter<CurrencySaved, FavouritesAdapter.ViewHolder>(DiffCallback()) {

    private var onDeleteCurrencyButtonClicked: ((CurrencySaved) -> Unit)? = null
    fun setOnDeleteCurrencyListener(listener: (CurrencySaved) -> Unit) {
        onDeleteCurrencyButtonClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRateSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(val binding: ItemRateSavedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencySaved: CurrencySaved) {
            binding.currency = currencySaved
            binding.ivDelete.setOnClickListener {
                onDeleteCurrencyButtonClicked?.invoke(currencySaved)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CurrencySaved>() {
        override fun areItemsTheSame(oldItem: CurrencySaved, newItem: CurrencySaved): Boolean = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: CurrencySaved, newItem: CurrencySaved): Boolean = oldItem == newItem
    }
}
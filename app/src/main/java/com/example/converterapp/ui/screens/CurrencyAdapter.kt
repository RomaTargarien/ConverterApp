package com.example.converterapp.ui.screens


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.R
import com.example.converterapp.databinding.ItemRateBinding
import com.example.converterapp.model.ui.Rate

class CurrencyAdapter : ListAdapter<Rate, CurrencyAdapter.ViewHolder>(DiffCallback()) {

    private var onSaveCurrencyClicked: ((Rate) -> Unit)? = null
    fun setOnSaveCurrencyListener(listener: (Rate) -> Unit) {
        onSaveCurrencyClicked = listener
    }

    private var onDeleteCurrencyClicked: ((Rate) -> Unit)? = null
    fun setOnDeleteCurrencyClicked(listener: (Rate) -> Unit) {
        onDeleteCurrencyClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRateBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            holder.bind(currentList[position])
        } else {
            holder.bind(currentList[position], payloads)
        }
    }

    inner class ViewHolder(val binding: ItemRateBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var rate: Rate

        init {
            binding.ivSaveRate.setOnClickListener {
                if (rate.isSaved) {
                    onDeleteCurrencyClicked?.invoke(rate)
                } else {
                    onSaveCurrencyClicked?.invoke(rate)
                }
            }
        }

        fun bind(rate: Rate) {
            this.rate = rate
            binding.rate = rate
            binding.ivSaveRate.setImageResource(
                if (rate.isSaved) R.drawable.ic_baseline_favourites_fill_dark_pink
                else R.drawable.ic_baseline_favourites_gray
            )
        }

        fun bind(rate: Rate, payloads: List<Any>) {
            this.rate = rate
            binding.ivSaveRate.setImageResource(
                if (payloads.last() as Boolean) R.drawable.ic_baseline_favourites_fill_dark_pink
                else R.drawable.ic_baseline_favourites_gray
            )
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Rate>() {
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean = (oldItem.name == newItem.name && oldItem.value == newItem.value)

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean = (oldItem.isSaved == newItem.isSaved)

        override fun getChangePayload(oldItem: Rate, newItem: Rate): Any? {
            if (oldItem.isSaved != newItem.isSaved) return newItem.isSaved
            return super.getChangePayload(oldItem, newItem)
        }
    }
}
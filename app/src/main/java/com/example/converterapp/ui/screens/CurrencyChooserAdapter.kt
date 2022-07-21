package com.example.converterapp.ui.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.R
import com.example.converterapp.databinding.ItemRateChooseBinding
import com.example.converterapp.model.ui.Rate
import com.example.converterapp.model.ui.SortOption
import com.squareup.picasso.Picasso

class CurrencyChooserAdapter : ListAdapter<Rate, CurrencyChooserAdapter.ViewHolder>(DiffCallback()) {

    private var onSortOptionClicked: ((Rate) -> Unit)? = null
    fun setOnSortOptionListener(listener: (Rate) -> Unit) {
        onSortOptionClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRateChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(val binding: ItemRateChooseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: Rate) {
            Picasso.get().load("https://www.countryflagicons.com/FLAT/64/${rate.name.subSequence(0..1)}.png")
                .error(R.drawable.ic_icon_home)
                .into(binding.ivRateIconFlag)
            binding.cvContent.setOnClickListener {
                onSortOptionClicked?.invoke(rate)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Rate>() {
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem == newItem
    }
}
package com.example.converterapp.ui.screens.popular.sort

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.R
import com.example.converterapp.databinding.ItemSortOptionBinding
import com.example.converterapp.model.ui.SortOption
import com.example.converterapp.util.ext.animateColorTransition

class SortAdapter : ListAdapter<SortOption, SortAdapter.ViewHolder>(DiffCallback()) {

    private var onSortOptionClicked: ((SortOption) -> Unit)? = null
    fun setOnSortOptionListener(listener: (SortOption) -> Unit) {
        onSortOptionClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSortOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.bind(currentList[position], payloads)
        }
    }

    inner class ViewHolder(val binding: ItemSortOptionBinding) : RecyclerView.ViewHolder(binding.root) {

        private var startContainerColor: Int
        private var endContainerColor: Int
        private lateinit var sortOption: SortOption

        init {
            startContainerColor = ContextCompat.getColor(itemView.context, R.color.white)
            endContainerColor = ContextCompat.getColor(itemView.context, R.color.dark_pink)
            binding.container.setOnClickListener {
                onSortOptionClicked?.invoke(sortOption)
            }
        }

        fun bind(sortOption: SortOption) {
            this.sortOption = sortOption
            binding.tvSortTitle.text = sortOption.sortOption.name
            binding.container.apply {
                setBackgroundColor(if (sortOption.isChecked) endContainerColor else startContainerColor)
            }
        }

        fun bind(sortOption: SortOption, payloads: List<Any>) {
            this.sortOption = sortOption
            val isChecked = payloads.last() as Boolean
            if (isChecked) {
                binding.container.animateColorTransition(startContainerColor, endContainerColor)
            } else {
                binding.container.animateColorTransition(endContainerColor, startContainerColor)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SortOption>() {
        override fun areItemsTheSame(oldItem: SortOption, newItem: SortOption): Boolean = oldItem.sortOption == newItem.sortOption

        override fun areContentsTheSame(oldItem: SortOption, newItem: SortOption): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: SortOption, newItem: SortOption): Any? {
            if (oldItem.isChecked != newItem.isChecked) return newItem.isChecked
            return super.getChangePayload(oldItem, newItem)
        }
    }
}


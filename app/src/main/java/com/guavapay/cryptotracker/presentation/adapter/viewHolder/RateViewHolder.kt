package com.guavapay.cryptotracker.presentation.adapter.viewHolder

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.guavapay.cryptotracker.databinding.ItemRateBinding
import com.guavapay.cryptotracker.domain.model.other.Rate

class RateViewHolder(
    val binding: ItemRateBinding,
    val context: Context
) : RecyclerView.ViewHolder(binding.root)

object RateDiffCallback : DiffUtil.ItemCallback<Rate>() {
    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem == newItem
    }
}
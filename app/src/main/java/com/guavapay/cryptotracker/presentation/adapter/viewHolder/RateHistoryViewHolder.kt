package com.guavapay.cryptotracker.presentation.adapter.viewHolder

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.databinding.ItemRateHistoryBinding

class RateHistoryViewHolder(
    val binding: ItemRateHistoryBinding,
    val context: Context
) : RecyclerView.ViewHolder(binding.root)

object RateRangeDiffCallback : DiffUtil.ItemCallback<CryptoRange>() {
    override fun areItemsTheSame(oldItem: CryptoRange, newItem: CryptoRange): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CryptoRange, newItem: CryptoRange): Boolean {
        return oldItem == newItem
    }
}
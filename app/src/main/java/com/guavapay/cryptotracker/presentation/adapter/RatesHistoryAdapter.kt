package com.guavapay.cryptotracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.databinding.ItemRateHistoryBinding
import com.guavapay.cryptotracker.presentation.adapter.viewHolder.RateHistoryViewHolder
import com.guavapay.cryptotracker.presentation.adapter.viewHolder.RateRangeDiffCallback

class RatesHistoryAdapter : ListAdapter<CryptoRange, RateHistoryViewHolder>(RateRangeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHistoryViewHolder {
        val binding: ItemRateHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_rate_history, parent, false
        )

        return RateHistoryViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: RateHistoryViewHolder, index: Int) {
        val range = getItem(index)

        holder.binding.apply {
            this.range = range
        }
    }
}
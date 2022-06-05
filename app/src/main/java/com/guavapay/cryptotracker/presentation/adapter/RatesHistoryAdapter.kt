package com.guavapay.cryptotracker.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.databinding.ItemRateHistoryBinding
import com.guavapay.cryptotracker.domain.model.other.Rate

class RatesHistoryAdapter(
    private val ranges: ArrayList<CryptoRange?>,
    private val context: Context
) : RecyclerView.Adapter<RatesHistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRateHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_rate_history, parent, false
        )

        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        val range = ranges[index]

        holder.binding.apply {
            this.range = range
        }
    }

    override fun getItemCount(): Int {
        return ranges.size
    }

    class ViewHolder(
        val binding: ItemRateHistoryBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root)


    interface OnHolderClickListener {
        fun onHolderItemClick(item: Rate)
    }
}
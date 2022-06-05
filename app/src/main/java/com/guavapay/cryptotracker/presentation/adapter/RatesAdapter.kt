package com.guavapay.cryptotracker.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.databinding.ItemRateBinding
import com.guavapay.cryptotracker.domain.model.other.Rate

class RatesAdapter(
    private val bonusList: List<Rate>,
    private val context: Context
) : RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    lateinit var onHolderClickListener: OnHolderClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_rate, parent, false
        )

        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        val rate = bonusList[index]

        holder.binding.apply {
            this.onClickListener = onHolderClickListener
            this.rate = rate
        }
    }

    override fun getItemCount(): Int {
        return bonusList.size
    }

    class ViewHolder(
        val binding: ItemRateBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root)


    interface OnHolderClickListener {
        fun onHolderItemClick(item: Rate)
    }
}
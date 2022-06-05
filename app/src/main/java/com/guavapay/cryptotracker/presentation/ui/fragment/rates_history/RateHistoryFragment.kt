package com.guavapay.cryptotracker.presentation.ui.fragment.rates_history

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.databinding.FragmentRatesRangeHistoryBinding
import com.guavapay.cryptotracker.presentation.adapter.RatesHistoryAdapter
import com.guavapay.cryptotracker.presentation.base.mvi_base.BaseMviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateHistoryFragment :
    BaseMviFragment<RatesHistoryIntent, RatesHistoryState, FragmentRatesRangeHistoryBinding, RatesHistoryViewModel>() {
    private lateinit var adapter: RatesHistoryAdapter
    private val ratesList = ArrayList<CryptoRange?>()
    private lateinit var viewModel: RatesHistoryViewModel
    private val TAG = "RateHistoryFragment"

    override fun getViewModel(): RatesHistoryViewModel {
        viewModel = ViewModelProvider(this)[RatesHistoryViewModel::class.java]
        return viewModel
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_rates_range_history
    }

    override fun initUI() {
        initRecyclerView()
    }

    override fun initEVENT() {
        val bundle = RateHistoryFragmentArgs.fromBundle(requireArguments())
        dispatchIntent(RatesHistoryIntent.FetchHistoryRange(bundle.type))
    }

    override fun render(state: RatesHistoryState) {
        when (state) {
            is RatesHistoryState.RatesList -> addRates(state.ranges)
            is RatesHistoryState.PopBackFragment -> findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        adapter = RatesHistoryAdapter(ratesList, requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        viewDataBinding.recyclerView.adapter = adapter
        viewDataBinding.recyclerView.layoutManager = layoutManager
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addRates(rates: List<CryptoRange>?) {
        rates?.let {
            ratesList.addAll(it)
            viewDataBinding.isEmpty = it.isEmpty()
        }
        adapter.notifyDataSetChanged()
    }
}
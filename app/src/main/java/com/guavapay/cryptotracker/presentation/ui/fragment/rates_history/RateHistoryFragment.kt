package com.guavapay.cryptotracker.presentation.ui.fragment.rates_history

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var viewModel: RatesHistoryViewModel

    override fun getViewModel(): RatesHistoryViewModel {
        viewModel = ViewModelProvider(this)[RatesHistoryViewModel::class.java]
        return viewModel
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_rates_range_history
    }

    override fun initUI() {
        viewDataBinding.viewModel = viewModel
        initRecyclerView()
    }

    override fun initEVENT() {
        val bundle = RateHistoryFragmentArgs.fromBundle(requireArguments())
        dispatchIntent(RatesHistoryIntent.FetchHistoryRange(bundle.type))
    }

    override fun render(state: RatesHistoryState) {
        when (state) {
            is RatesHistoryState.RatesList -> submitList(state.ranges)
            is RatesHistoryState.PopBackFragment -> findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        adapter = RatesHistoryAdapter()
        viewDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@RateHistoryFragment.adapter
        }
    }

    private fun submitList(ranges: List<CryptoRange>?) {
        adapter.submitList(ranges)
        viewDataBinding.isEmpty = ranges?.isEmpty()
    }
}
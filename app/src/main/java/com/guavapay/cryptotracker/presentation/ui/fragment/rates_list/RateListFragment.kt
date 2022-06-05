package com.guavapay.cryptotracker.presentation.ui.fragment.rates_list

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.databinding.FragmentRatesListBinding
import com.guavapay.cryptotracker.domain.model.other.Rate
import com.guavapay.cryptotracker.presentation.adapter.RatesAdapter
import com.guavapay.cryptotracker.presentation.base.mvi_base.BaseMviFragment
import com.guavapay.cryptotracker.presentation.ui.dialog.DialogSetRange
import com.guavapay.cryptotracker.presentation.util.ServiceUtil
import com.guavapay.cryptotracker.presentation.util.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateListFragment :
    BaseMviFragment<RatesIntent, RatesState, FragmentRatesListBinding, RatesViewModel>() {
    private lateinit var adapter: RatesAdapter
    private val ratesList = ArrayList<Rate>()
    private lateinit var viewModel: RatesViewModel

    override fun getViewModel(): RatesViewModel {
        viewModel = ViewModelProvider(this)[RatesViewModel::class.java]
        return viewModel
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_rates_list
    }

    override fun initUI() {
        initRecyclerView()

        ServiceUtil.startCheckRateService(requireContext())
    }

    override fun initEVENT() {
        adapter.onHolderClickListener = viewModel.onHolderClickHandler
    }

    override fun render(state: RatesState) {
        when (state) {
            is RatesState.RatesList -> addRates(state.rates)
            is RatesState.ShowRangeDialog -> showRangeDialog(state.range.cryptoName)
            is RatesState.PopBackFragment -> findNavController().popBackStack()
        }
    }

    private fun showRangeDialog(cryptoName: String) {
        val dialog = DialogSetRange(cryptoName, {
            dispatchIntent(RatesIntent.InsertRange(it))
        }) {
            safeNavigate(RateListFragmentDirections.actionNavigationHomeToNavigationRateHistory(type = it))
        }
        dialog.show(childFragmentManager, "dialog")
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        adapter = RatesAdapter(ratesList, requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        viewDataBinding.recyclerView.adapter = adapter
        viewDataBinding.recyclerView.layoutManager = layoutManager
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addRates(rates: ArrayList<Rate>) {
        ratesList.addAll(rates)
        adapter.notifyDataSetChanged()
    }
}
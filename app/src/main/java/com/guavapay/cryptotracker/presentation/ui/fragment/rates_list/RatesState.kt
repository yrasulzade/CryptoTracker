package com.guavapay.cryptotracker.presentation.ui.fragment.rates_list

import com.guavapay.cryptotracker.domain.model.other.Rate
import com.guavapay.cryptotracker.presentation.base.mvi_base.ViewState

sealed class RatesState : ViewState {
    data class RatesList(val rates: ArrayList<Rate>) : RatesState()
    data class ShowRangeDialog(val range: Rate) : RatesState()
}

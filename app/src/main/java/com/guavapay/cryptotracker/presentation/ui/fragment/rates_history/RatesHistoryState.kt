package com.guavapay.cryptotracker.presentation.ui.fragment.rates_history

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.other.Rate
import com.guavapay.cryptotracker.presentation.base.mvi_base.ViewState

sealed class RatesHistoryState : ViewState {
    data class RatesList(val ranges: List<CryptoRange>?) : RatesHistoryState()
    object PopBackFragment : RatesHistoryState()
}

package com.guavapay.cryptotracker.presentation.ui.fragment.rates_history

import com.guavapay.cryptotracker.presentation.base.mvi_base.ViewIntent

sealed class RatesHistoryIntent : ViewIntent {
    data class FetchHistoryRange(val type: String) : RatesHistoryIntent()
}

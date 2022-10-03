package com.guavapay.cryptotracker.presentation.ui.fragment.rates_list

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.presentation.base.mvi_base.ViewIntent

sealed class RatesIntent : ViewIntent {
    data class InsertRange(val range: CryptoRange) : RatesIntent()
}

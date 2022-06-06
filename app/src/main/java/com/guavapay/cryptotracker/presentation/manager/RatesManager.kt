package com.guavapay.cryptotracker.presentation.manager

import com.guavapay.cryptotracker.domain.model.response.CryptoDetail
import kotlinx.coroutines.flow.MutableSharedFlow

object RatesManager {
    val sharadFlow = MutableSharedFlow<CryptoDetail>(1)
}
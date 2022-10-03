package com.guavapay.cryptotracker.presentation.ui.fragment.rates_list

import androidx.lifecycle.viewModelScope
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.model.other.Rate
import com.guavapay.cryptotracker.domain.useCase.CryptoRateUseCase
import com.guavapay.cryptotracker.presentation.adapter.RatesAdapter
import com.guavapay.cryptotracker.presentation.base.mvi_base.BaseViewModel
import com.guavapay.cryptotracker.presentation.manager.RatesManager
import com.guavapay.cryptotracker.presentation.util.Utils.roundFloatTwoDecimal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val cryptoRateUseCase: CryptoRateUseCase) :
    BaseViewModel<RatesIntent, RatesState>() {

    init {
        fetchRates()
    }

    private fun fetchRates() {

        viewModelScope.launch {
            RatesManager.sharadFlow.collectLatest {
                val rates = ArrayList<Rate>()
                rates.add(Rate(CryptoType.BTC.name, it.btc.value.roundFloatTwoDecimal()))
                rates.add(Rate(CryptoType.ETH.name, it.eth.value.roundFloatTwoDecimal()))
                rates.add(Rate(CryptoType.XRP.name, it.xrp.value.roundFloatTwoDecimal()))

                mState.value = RatesState.RatesList(rates)
            }
        }
    }

    private fun saveCryptoRange(cryptoRange: CryptoRange) {
        viewModelScope.launch {
            cryptoRateUseCase.execute(cryptoRange)
        }
    }

    val onHolderClickHandler = object : RatesAdapter.OnHolderClickListener {
        override fun onHolderItemClick(item: Rate) {
            mState.value = RatesState.ShowRangeDialog(item)
        }
    }

    override fun handleIntent(intent: RatesIntent) {
        when (intent) {
            is RatesIntent.InsertRange -> saveCryptoRange(intent.range)
        }
    }
}
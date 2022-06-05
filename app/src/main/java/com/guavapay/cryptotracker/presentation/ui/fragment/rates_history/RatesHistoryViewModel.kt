package com.guavapay.cryptotracker.presentation.ui.fragment.rates_history

import androidx.lifecycle.viewModelScope
import com.guavapay.cryptotracker.data.database.dao.CryptoRatesDao
import com.guavapay.cryptotracker.presentation.base.mvi_base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesHistoryViewModel @Inject constructor(
    private val dao: CryptoRatesDao
) :
    BaseViewModel<RatesHistoryIntent, RatesHistoryState>() {
    private val TAG = "RatesHistoryViewModel"

    private fun getCryptoDataFlow(type: String) {
        viewModelScope.launch {
            flow {
                emit(dao.getSpecificCryptoList(type))
            }.flowOn(IO)
                .collect {
                    mState.value = RatesHistoryState.RatesList(it)
                }
        }
    }


    override fun handleIntent(intent: RatesHistoryIntent) {
        when (intent) {
            is RatesHistoryIntent.FetchHistoryRange -> getCryptoDataFlow(intent.type)
        }
    }
}
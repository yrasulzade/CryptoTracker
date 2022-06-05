package com.guavapay.cryptotracker.presentation.ui.fragment.rates_list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.guavapay.cryptotracker.data.database.dao.CryptoRatesDao
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.model.other.Rate
import com.guavapay.cryptotracker.domain.useCase.CryptoRateUseCase
import com.guavapay.cryptotracker.domain.useCase.ExchangeRatesUseCase
import com.guavapay.cryptotracker.presentation.adapter.RatesAdapter
import com.guavapay.cryptotracker.presentation.base.mvi_base.BaseViewModel
import com.guavapay.cryptotracker.presentation.util.Utils.roundFloatTwoDecimal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val bonusInfoUseCase: ExchangeRatesUseCase,
    private val cryptoRateUseCase: CryptoRateUseCase,
    private val dao: CryptoRatesDao
) :
    BaseViewModel<RatesIntent, RatesState>() {
    private val TAG = "RatesViewModel"

    init {
        fetchRates()
        testGetRanges()
    }

    private fun fetchRates() {
        viewModelScope.launch {
            flow {
                emit(bonusInfoUseCase.execute("cubiex", "btc,eth,xrp"))
            }
                .flowOn(IO)
                .onStart { loading(true) }
                .onCompletion { loading(false) }
                .catch { errorLiveData.value = it }
                .collect {
                    val rates = ArrayList<Rate>()
                    rates.add(Rate(CryptoType.BTC.name, it.btc.value.roundFloatTwoDecimal()))
                    rates.add(Rate(CryptoType.ETH.name, it.eth.value.roundFloatTwoDecimal()))
                    rates.add(Rate(CryptoType.XRP.name, it.xrp.value.roundFloatTwoDecimal()))

                    mState.value = RatesState.RatesList(rates)
                    Log.d(
                        "RatesViewModel",
                        "fetchRates: ${it.btc} ${it.eth.value.roundFloatTwoDecimal()} ${it.xrp.value.roundFloatTwoDecimal()}"
                    )
                }
        }
    }

    private fun testGetRanges() {
        viewModelScope.launch {
            flow { emit(dao.getThreeCryptoData()) }.flowOn(IO).collect {
                it.collectLatest {
                    it.forEach {
                        Log.d(
                            TAG,
                            "testGetRanges: ${it.cryptoName} ${it.minValue}  ${it.maxValue}  ${it.id} "
                        )
                    }
                }
            }
        }


        viewModelScope.launch {
            flow { emit(dao.getSpecificCryptoFlow(CryptoType.BTC.name)) }
                .flowOn(IO).collect {
                    it.collectLatest {
                        Log.d(
                            TAG,
                            "testGetRanges: latestValue ${it?.cryptoName} ${it?.minValue}  ${it?.maxValue}"
                        )
                    }
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
            is RatesIntent.PopBackFragment -> mState.postValue(RatesState.PopBackFragment)
        }
    }
}
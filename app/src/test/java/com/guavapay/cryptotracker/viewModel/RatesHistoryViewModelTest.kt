package com.guavapay.cryptotracker.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guavapay.cryptotracker.MainCoroutineRule
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.useCase.SpecificCryptoRangeListUseCase
import com.guavapay.cryptotracker.getOrAwaitValue
import com.guavapay.cryptotracker.presentation.ui.fragment.rates_history.RatesHistoryIntent
import com.guavapay.cryptotracker.presentation.ui.fragment.rates_history.RatesHistoryState
import com.guavapay.cryptotracker.presentation.ui.fragment.rates_history.RatesHistoryViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class RatesHistoryViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val cryptoList = ArrayList<CryptoRange>()

    private val specificCryptoRangeListUseCase = mockk<SpecificCryptoRangeListUseCase>()
    private lateinit var viewModel: RatesHistoryViewModel

    @Before
    fun initiateViewModel() {
        viewModel = RatesHistoryViewModel(specificCryptoRangeListUseCase)
    }

    @Test
    fun `test get crypto data flow`() {
        val cryptoRange = CryptoRange(CryptoType.BTC.name, 12.5, 14.0)

        cryptoList.add(cryptoRange)

        runTest {
            coEvery { specificCryptoRangeListUseCase.execute(CryptoType.BTC.name) } returns cryptoList
        }

        viewModel.handleIntent(RatesHistoryIntent.FetchHistoryRange(CryptoType.BTC.name))


        MatcherAssert.assertThat(
            viewModel.state.getOrAwaitValue(),
            `is`(RatesHistoryState.RatesList(cryptoList))
        )
    }
}
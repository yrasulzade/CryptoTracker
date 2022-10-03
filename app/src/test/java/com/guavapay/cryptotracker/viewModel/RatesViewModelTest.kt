package com.guavapay.cryptotracker.viewModel

import com.guavapay.cryptotracker.MainCoroutineRule
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.useCase.CryptoRateUseCase
import com.guavapay.cryptotracker.presentation.ui.fragment.rates_list.RatesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class RatesViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val cryptoRateUseCase = mockk<CryptoRateUseCase>()
    private lateinit var viewModel: RatesViewModel

    @Before
    fun initiateViewModel() {
        viewModel = RatesViewModel(cryptoRateUseCase)
    }

    @Test
    fun `test insert crypto range use case`() {
        val range = CryptoRange(CryptoType.BTC.name, 10.0, 2000.0)

        coEvery { cryptoRateUseCase.execute(range) } returns Unit

        runTest {
            cryptoRateUseCase.execute(range)
        }
        verify(exactly = 1) { runTest { cryptoRateUseCase.execute(range) } }
    }
}
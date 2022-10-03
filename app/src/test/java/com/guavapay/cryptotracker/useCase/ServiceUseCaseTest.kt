package com.guavapay.cryptotracker.useCase

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.model.response.CryptoDetail
import com.guavapay.cryptotracker.domain.model.response.CryptoValue
import com.guavapay.cryptotracker.domain.model.response.RateResponse
import com.guavapay.cryptotracker.domain.useCase.ExchangeRatesUseCase
import com.guavapay.cryptotracker.domain.useCase.LastSetRangeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class ServiceUseCaseTest {

    private val exchangeRatesUseCase = mockk<ExchangeRatesUseCase>()
    private val lastSetRangeUseCase = mockk<LastSetRangeUseCase>()

    @Test
    fun `test insert crypto range use case`() {
        val rateResponse = RateResponse(
            CryptoDetail(
                CryptoValue(1.0, CryptoType.BTC),
                CryptoValue(16.87, CryptoType.ETH),
                CryptoValue(2433.0, CryptoType.XRP),
            )
        )

        coEvery { exchangeRatesUseCase.execute() } returns rateResponse.rates

        runTest {
            exchangeRatesUseCase.execute()
        }
        verify(exactly = 1) { runTest { exchangeRatesUseCase.execute() } }
    }

    @Test
    fun `test last set range use case`() {

        val range = CryptoRange(CryptoType.BTC.name, 10.0, 20.0)

        coEvery { lastSetRangeUseCase.execute(CryptoType.BTC.name) } returns range

        runTest {
            lastSetRangeUseCase.execute(CryptoType.BTC.name)
        }
        verify(exactly = 1) { runTest { lastSetRangeUseCase.execute(CryptoType.BTC.name) } }
    }
}
package com.guavapay.cryptotracker.repository

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.model.response.CryptoDetail
import com.guavapay.cryptotracker.domain.model.response.CryptoValue
import com.guavapay.cryptotracker.domain.model.response.RateResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RatesRepositoryImplTest {
    private lateinit var fakeRateDataSource: FakeRatesDataSource
    private lateinit var rateResponse: RateResponse
    private lateinit var cryptoRange: CryptoRange
    private val cryptoList = ArrayList<CryptoRange>()

    @Before
    fun `create repository`() {
        rateResponse = RateResponse(
            CryptoDetail(
                CryptoValue(1.0, CryptoType.BTC),
                CryptoValue(16.87, CryptoType.ETH),
                CryptoValue(2433.0, CryptoType.XRP),
            )
        )
        cryptoRange = CryptoRange(CryptoType.BTC.name, 12.5, 14.0)

        cryptoList.add(cryptoRange)

        fakeRateDataSource = FakeRatesDataSource(rateResponse, cryptoRange, cryptoList)
    }

    @Test
    fun `get exchange rates`() {
        runTest {
            assertThat(
                rateResponse, IsEqual(fakeRateDataSource.getExchangeRates())
            )
        }
    }

    @Test
    fun `insert crypto range`() {
        runTest {
            assertThat(
                Unit, IsEqual(fakeRateDataSource.insertCryptoRange(cryptoRange))
            )
        }
    }

    @Test
    fun `get specific crypto`() {
        runTest {
            assertThat(
                cryptoRange, IsEqual(fakeRateDataSource.getLatestCryptoRange(CryptoType.BTC.name))
            )
        }
    }

    @Test
    fun `get specific crypto list`() {
        runTest {
            assertThat(
                cryptoList, IsEqual(fakeRateDataSource.getSpecificCryptoList(CryptoType.BTC.name))
            )
        }
    }
}










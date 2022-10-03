package com.guavapay.cryptotracker.useCase

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.useCase.SpecificCryptoRangeListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class SpecificCryptoRangeUseCaseTest {

    private val specificCryptoRangeListUseCase = mockk<SpecificCryptoRangeListUseCase>()
    private val cryptoList = ArrayList<CryptoRange>()

    @Test
    fun `specific crypto range list use case`() {
        val cryptoRange = CryptoRange(CryptoType.BTC.name, 12.5, 14.0)

        cryptoList.add(cryptoRange)

        coEvery { specificCryptoRangeListUseCase.execute(CryptoType.BTC.name) } returns cryptoList

        runTest {
            specificCryptoRangeListUseCase.execute(CryptoType.BTC.name)
        }
        verify(exactly = 1) { runTest { specificCryptoRangeListUseCase.execute(CryptoType.BTC.name) } }
    }
}
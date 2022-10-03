package com.guavapay.cryptotracker.repository

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.response.RateResponse
import com.guavapay.cryptotracker.domain.repository.RatesRepository

class FakeRatesDataSource(
    private val rateResponse: RateResponse,
    private val cryptoRange: CryptoRange,
    private val cryptoList: List<CryptoRange>?
) : RatesRepository {

    override suspend fun getExchangeRates(): RateResponse {
        return rateResponse
    }

    override suspend fun insertCryptoRange(cryptoRange: CryptoRange) {

    }

    override suspend fun getLatestCryptoRange(cryptoType: String): CryptoRange {
        return cryptoRange
    }

    override suspend fun getSpecificCryptoList(cryptoType: String): List<CryptoRange>? {
        return cryptoList
    }
}
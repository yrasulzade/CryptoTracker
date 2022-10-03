package com.guavapay.cryptotracker.domain.repository

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.response.RateResponse

interface RatesRepository {
    suspend fun getExchangeRates(): RateResponse
    suspend fun insertCryptoRange(cryptoRange: CryptoRange)
    suspend fun getLatestCryptoRange(cryptoType: String): CryptoRange?
    suspend fun getSpecificCryptoList(cryptoType: String): List<CryptoRange>?
}
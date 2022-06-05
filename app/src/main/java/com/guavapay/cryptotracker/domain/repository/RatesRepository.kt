package com.guavapay.cryptotracker.domain.repository

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.response.RateResponse

interface RatesRepository {
    suspend fun getExchangeRates(query: String, vs_currencies: String): RateResponse
    suspend fun insertCryptoRange(cryptoRange: CryptoRange)
    suspend fun getSpecificCrypto(cryptoType: String):CryptoRange?
}
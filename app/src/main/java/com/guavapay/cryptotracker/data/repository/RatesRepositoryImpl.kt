package com.guavapay.cryptotracker.data.repository

import com.guavapay.cryptotracker.data.api.ApiService
import com.guavapay.cryptotracker.data.database.dao.CryptoRatesDao
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.model.response.RateResponse
import com.guavapay.cryptotracker.domain.repository.RatesRepository
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cryptoRatesDao: CryptoRatesDao
) :
    RatesRepository {
    override suspend fun getExchangeRates(query: String, vs_currencies: String): RateResponse {
        return apiService.getExchangeRates(query, vs_currencies)
    }

    override suspend fun insertCryptoRange(cryptoRange: CryptoRange) {
        return cryptoRatesDao.insert(cryptoRange)
    }

    override suspend fun getSpecificCrypto(cryptoType: String): CryptoRange? {
        return cryptoRatesDao.getSpecificCrypto(cryptoType)
    }
}
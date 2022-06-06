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
    override suspend fun getExchangeRates(): RateResponse {
        return apiService.getExchangeRates()
    }

    override suspend fun insertCryptoRange(cryptoRange: CryptoRange) {
        return cryptoRatesDao.insert(cryptoRange)
    }

    override suspend fun getSpecificCrypto(cryptoType: String): CryptoRange? {
        return cryptoRatesDao.getSpecificCrypto(cryptoType)
    }

    override suspend fun getSpecificCryptoList(cryptoType: String): List<CryptoRange>? {
        return cryptoRatesDao.getSpecificCryptoList(cryptoType)
    }
}
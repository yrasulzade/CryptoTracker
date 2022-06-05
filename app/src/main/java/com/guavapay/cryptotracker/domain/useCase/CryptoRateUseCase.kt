package com.guavapay.cryptotracker.domain.useCase

import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.domain.repository.RatesRepository
import javax.inject.Inject

class CryptoRateUseCase @Inject constructor(private val repository: RatesRepository) {
    suspend fun execute(cryptoRange: CryptoRange) = repository.insertCryptoRange(cryptoRange)
}
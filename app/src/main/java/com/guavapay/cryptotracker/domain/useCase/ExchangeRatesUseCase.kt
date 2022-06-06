package com.guavapay.cryptotracker.domain.useCase

import com.guavapay.cryptotracker.domain.repository.RatesRepository
import javax.inject.Inject

class ExchangeRatesUseCase @Inject constructor(private val repository: RatesRepository) {
    suspend fun execute() = repository.getExchangeRates().rates
}
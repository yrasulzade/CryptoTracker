package com.guavapay.cryptotracker.domain.useCase

import com.guavapay.cryptotracker.domain.repository.RatesRepository
import javax.inject.Inject

class ExchangeRatesUseCase @Inject constructor(private val repository: RatesRepository) {
    suspend fun execute(query: String, vs_currencies: String) =
        repository.getExchangeRates(query, vs_currencies).rates
}
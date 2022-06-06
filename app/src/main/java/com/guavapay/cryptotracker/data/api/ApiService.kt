package com.guavapay.cryptotracker.data.api

import com.guavapay.cryptotracker.data.api.Endpoints.RATES
import com.guavapay.cryptotracker.domain.model.response.RateResponse
import retrofit2.http.GET

interface ApiService {

    @GET(RATES)
    suspend fun getExchangeRates(): RateResponse

}
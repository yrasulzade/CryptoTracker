package com.guavapay.cryptotracker.data.api

import com.guavapay.cryptotracker.data.api.Endpoints.RATES
import com.guavapay.cryptotracker.domain.model.response.RateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(RATES)
    suspend fun getExchangeRates(
        @Query("ids") query: String,
        @Query("vs_currencies") vs_currencies: String
    ): RateResponse

}
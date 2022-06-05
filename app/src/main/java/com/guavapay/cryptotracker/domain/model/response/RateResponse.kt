package com.guavapay.cryptotracker.domain.model.response

import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class RateResponse(
    @Json(name = "rates") val rates: CryptoDetail
)

data class CryptoDetail(
    @Json(name = "btc") val btc: CryptoValue,
    @Json(name = "eth") val eth: CryptoValue,
    @Json(name = "xrp") val xrp: CryptoValue,
)

data class CryptoValue(
    @Json(name = "value") val value: Double,
    @Json(name = "unit") val unit: CryptoType
)
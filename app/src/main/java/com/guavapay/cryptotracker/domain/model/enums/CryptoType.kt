package com.guavapay.cryptotracker.domain.model.enums

import com.squareup.moshi.Json

enum class CryptoType {
    @Json(name = "BTC")
    BTC,

    @Json(name = "ETH")
    ETH,

    @Json(name = "XRP")
    XRP
}
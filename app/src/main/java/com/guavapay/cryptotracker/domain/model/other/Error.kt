package com.guavapay.cryptotracker.domain.model.other

data class Error(
    val message: String? = null,
    val type: String? = null,
    val title: String? = null,
    val status: Int? = null
)

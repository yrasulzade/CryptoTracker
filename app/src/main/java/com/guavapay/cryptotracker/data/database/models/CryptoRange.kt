package com.guavapay.cryptotracker.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_table")
data class CryptoRange(
    var cryptoName: String,
    var minValue: Double,
    var maxValue: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
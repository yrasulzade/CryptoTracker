package com.guavapay.cryptotracker.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guavapay.cryptotracker.data.database.dao.CryptoRatesDao
import com.guavapay.cryptotracker.data.database.models.CryptoRange

@Database(
    entities = [CryptoRange::class],
    version = 2,
    exportSchema = false
)
abstract class CryptoRatesDatabase : RoomDatabase() {
    abstract fun cryptoRatesDao(): CryptoRatesDao
}

package com.guavapay.cryptotracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guavapay.cryptotracker.data.database.models.CryptoRange

@Dao
interface CryptoRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cryptoRange: CryptoRange)

    @Query("SELECT * FROM crypto_table WHERE cryptoName LIKE :cryptoName ORDER BY id DESC")
    fun getLatestCryptoRange(cryptoName: String): CryptoRange?

    @Query("SELECT * FROM crypto_table WHERE cryptoName LIKE :cryptoName ORDER BY id DESC")
    fun getSpecificCryptoList(cryptoName: String): List<CryptoRange>?
}
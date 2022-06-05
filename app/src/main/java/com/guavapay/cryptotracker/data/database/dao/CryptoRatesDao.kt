package com.guavapay.cryptotracker.data.database.dao

import androidx.room.*
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cryptoRange: CryptoRange)

    @Query("SELECT * FROM crypto_table ORDER BY id DESC")
    fun getCryptoDataFlow(): Flow<CryptoRange>

    @Query("SELECT * FROM crypto_table ORDER BY id DESC LIMIT 3")
    fun getThreeCryptoData(): Flow<List<CryptoRange>>

    @Query("SELECT count(*) FROM crypto_table")
    fun getSizeOfOfCryptoData(): Flow<Int>

    @Query("SELECT * FROM crypto_table ORDER BY id DESC LIMIT 7")
    fun getWeeklyData(): Flow<List<CryptoRange>>

    @Query("SELECT * FROM crypto_table ORDER BY id DESC LIMIT 30")
    fun getMonthlyData(): Flow<List<CryptoRange>>

    @Query("SELECT * FROM crypto_table ORDER BY id DESC")
    fun getCryptoData(): List<CryptoRange>

    @Update(entity = CryptoRange::class)
    suspend fun update(obj: CryptoRange)

    @Query("SELECT * FROM crypto_table WHERE cryptoName LIKE :cryptoName ORDER BY id DESC")
    fun getSpecificCryptoFlow(cryptoName: String): Flow<CryptoRange>

    @Query("SELECT * FROM crypto_table WHERE cryptoName LIKE :cryptoName ORDER BY id DESC")
    fun getSpecificCrypto(cryptoName: String): CryptoRange?

    @Query("SELECT * FROM crypto_table WHERE cryptoName LIKE :cryptoName ORDER BY id DESC")
    fun getSpecificCryptoList(cryptoName: String): List<CryptoRange>?
//
//    @Query("SELECT * FROM crypto_table WHERE date LIKE :date")
//    fun getSpecificDate(date: String): CryptoRange
//
//    @Query("DELETE FROM crypto_table WHERE date = :date")
//    fun deleteTrainingDataByDate(date: String)

    @Query("DELETE FROM crypto_table")
    suspend fun deleteTable()
}
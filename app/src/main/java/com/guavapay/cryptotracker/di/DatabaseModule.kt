package com.guavapay.cryptotracker.di

import android.content.Context
import androidx.room.Room
import com.guavapay.cryptotracker.data.database.db.CryptoRatesDatabase
import com.guavapay.cryptotracker.data.database.dao.CryptoRatesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesUserDao(dailyDatabase: CryptoRatesDatabase): CryptoRatesDao = dailyDatabase.cryptoRatesDao()

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context): CryptoRatesDatabase =
        Room.databaseBuilder(context, CryptoRatesDatabase::class.java, "CryptoRatesDatabase")
            .fallbackToDestructiveMigration()
            .build()
}
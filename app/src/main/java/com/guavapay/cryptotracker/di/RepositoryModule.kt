package com.guavapay.cryptotracker.di

import com.guavapay.cryptotracker.data.api.ApiService
import com.guavapay.cryptotracker.data.database.dao.CryptoRatesDao
import com.guavapay.cryptotracker.domain.repository.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.guavapay.cryptotracker.data.repository.RatesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDiscoverRepository(
        apiService: ApiService,
        cryptoRatesDao: CryptoRatesDao,
    ): RatesRepository {
        return RatesRepositoryImpl(apiService, cryptoRatesDao)
    }
}
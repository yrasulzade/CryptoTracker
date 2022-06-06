package com.guavapay.cryptotracker.di

import com.guavapay.cryptotracker.data.api.ApiService
import com.guavapay.cryptotracker.data.api.HttpInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://api.coingecko.com/api/v3/"

    @Provides
    @Singleton
    fun provideOkHttpClient(okHttpClient: HttpInterceptor) = run {
        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(okHttpClient)
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
        builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
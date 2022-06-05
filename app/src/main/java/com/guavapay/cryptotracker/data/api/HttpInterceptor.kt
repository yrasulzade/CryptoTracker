package com.guavapay.cryptotracker.data.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpInterceptor @Inject constructor(
    private val context: Context
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        return chain.proceed(request.build())
    }


}
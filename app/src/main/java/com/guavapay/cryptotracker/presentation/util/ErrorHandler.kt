package com.guavapay.cryptotracker.presentation.util

import android.content.Context
import android.util.Log
import com.guavapay.cryptotracker.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.guavapay.cryptotracker.presentation.util.Constants.SERVER_ERROR_CODE
import retrofit2.HttpException
import java.io.IOException

class ErrorHandler(private val context: Context) {
    @Throws(IOException::class)
    fun handleError(throwable: Throwable): String {
        throwable.printStackTrace()

        when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    SERVER_ERROR_CODE -> return context.getString(R.string.error_server_error)
                }

                Log.d("handleError", "handleError: ${throwable.code()}")

                val json = throwable.response()?.errorBody()!!.string()
                val error = convert(json)

                error?.message?.let {
                    return it
                }
            }
        }

        Log.d("handleError", "handleError: " + throwable.message.toString())
        return context.getString(R.string.general_error)
    }

    private fun convert(json: String): Error? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Error> =
            moshi.adapter(Error::class.java)

        return jsonAdapter.fromJson(json)
    }
}


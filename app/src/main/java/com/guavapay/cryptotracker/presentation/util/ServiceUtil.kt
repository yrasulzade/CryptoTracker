package com.guavapay.cryptotracker.presentation.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.guavapay.cryptotracker.presentation.services.CheckRateService

object ServiceUtil {
    private val TAG = "ServiceUtil"

    private fun isServiceRunning(
        context: Context,
        serviceClass: Class<*>
    ): Boolean {
        val manager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun startCheckRateService(context: Context) {

        if (!isServiceRunning(context, CheckRateService::class.java)) {
            Log.d("ServiceUtil", "start Service: ")

            val intent = Intent(context,CheckRateService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }

        } else {
            Log.d(TAG, " service is already running")
        }
    }
}
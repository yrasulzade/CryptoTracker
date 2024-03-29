package com.guavapay.cryptotracker.presentation.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.domain.model.enums.CryptoType
import com.guavapay.cryptotracker.domain.useCase.ExchangeRatesUseCase
import com.guavapay.cryptotracker.domain.useCase.LastSetRangeUseCase
import com.guavapay.cryptotracker.presentation.manager.RatesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CheckRateService : Service() {
    private val TAG = "CheckRateService"
    private val serviceNotificationID = 999
    private val warningNotificationID = 888
    private val backgroundScope = CoroutineScope(IO)
    private val notificationChannelId = "checkRateService"

    @Inject
    lateinit var exchangeRatesUseCase: ExchangeRatesUseCase

    @Inject
    lateinit var lastSetRangeUseCase: LastSetRangeUseCase

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(serviceNotificationID, createNotification())
    }

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel", NotificationManager.IMPORTANCE_LOW
            )
            channel.enableLights(true)
            channel.lightColor = Color.GRAY
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
        }

        val mBuilder =
            NotificationCompat.Builder(this, notificationChannelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Rates")
                .setContentText("Rates are being checked")
        return mBuilder.build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")

        backgroundScope.launch {
            while (true) {
                try {
                    val btcRate = lastSetRangeUseCase.execute(CryptoType.BTC.name)
                    val ethRate = lastSetRangeUseCase.execute(CryptoType.ETH.name)
                    val xrpRate = lastSetRangeUseCase.execute(CryptoType.XRP.name)

                    val response = exchangeRatesUseCase.execute()
                    RatesManager.sharadFlow.emit(response)

                    btcRate?.let {
                        if (ratesChanged(response.btc.value, it.minValue, it.maxValue)) {
                            setNotificationManager(response.btc.unit.name)
                        }
                    }
                    ethRate?.let {
                        if (ratesChanged(response.eth.value, it.minValue, it.maxValue)) {
                            setNotificationManager(response.eth.unit.name)
                        }
                    }
                    xrpRate?.let {
                        if (ratesChanged(response.xrp.value, it.minValue, it.maxValue)) {
                            setNotificationManager(response.xrp.unit.name)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(3000)
            }
        }


        return START_STICKY
    }

    private fun setNotificationManager(type: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(warningNotificationID, createWarningNotification(type).build())
    }

    private val createWarningNotification: (String) -> NotificationCompat.Builder = {
        NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.warning_rate_change, it))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setChannelId(notificationChannelId)
            .setContentText(getString(R.string.rate_change_description))
    }

    private fun ratesChanged(cryptoValue: Double, minValue: Double, maxValue: Double): Boolean {
        Log.d(TAG, "ratesChanged: $cryptoValue $minValue $maxValue")
        if (cryptoValue > maxValue || cryptoValue < minValue) {
            return true
        }
        return false
    }
}
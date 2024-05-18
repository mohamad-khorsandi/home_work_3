package com.example.home_work_3

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ChargeStatusService : Service() {
    private var chargeReceiver = ChargeStatusReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("mylog", "${intent?.action.toString()} command sent to service")

        when(intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun start() {
        Log.d("mylog", "start notification by service")

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(chargeReceiver, filter)

//        val notification = NotificationCompat.Builder(this, "main_channel")
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setContentTitle("charge status")
//            .setContentText("nothing to show")
//            .build()
//
//        startForeground(1, notification)
    }

    private fun stop() {
        Log.d("mylog", "stop notification by service")
        unregisterReceiver(chargeReceiver)
        stopSelf()
    }
    enum class Actions {
        START, STOP
    }

}
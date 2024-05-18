package com.example.home_work_3

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder


class ChargeStatusService : Service() {
    private var chargeReceiver = ChargeStatusReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

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
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(chargeReceiver, filter)
    }

    private fun stop() {
        unregisterReceiver(chargeReceiver)
        stopSelf()
    }

    enum class Actions {
        START, STOP
    }
}
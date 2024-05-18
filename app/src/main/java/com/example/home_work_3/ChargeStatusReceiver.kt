package com.example.home_work_3

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.BatteryManager

import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ChargeStatusReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val status = intent!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

        val batteryStatusMap = mutableMapOf(
            BatteryManager.BATTERY_STATUS_CHARGING to "CHARGING",
            BatteryManager.BATTERY_STATUS_NOT_CHARGING to "NOT CHARGING",
            BatteryManager.BATTERY_STATUS_FULL to "FULL",
            BatteryManager.BATTERY_STATUS_UNKNOWN to "UNKNOWN",
            BatteryManager.BATTERY_STATUS_DISCHARGING to "DISCHARGING"
        )

        MainActivity.reportSubject = "battery"
        MainActivity.reportStatus = (batteryStatusMap[status])!!
        val notificationMsg = "battery is ${batteryStatusMap[status]}"

        updateNotification(context!!, notificationMsg)
        Toast.makeText(context, notificationMsg, Toast.LENGTH_LONG).show()
        StatusLog("battery", (batteryStatusMap[status])!!, LogLevel.I).save().log()
    }

    private fun updateNotification(context: Context, data: String) {
        val notificationId = 1

        val notificationBuilder = NotificationCompat.Builder(context, "main_channel")
            .setContentTitle("CHARGE STATUS")
            .setContentText(data)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }
}

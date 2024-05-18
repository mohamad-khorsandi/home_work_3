package com.example.home_work_3

import android.bluetooth.BluetoothManager
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class StatusWorker(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val bluetoothManager = appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val isBluetoothEnabled = bluetoothManager.adapter?.isEnabled ?: false

        val bluetoothStatus = if (isBluetoothEnabled) "ON" else "OFF"
        StatusLog("bluetooth", bluetoothStatus, LogLevel.I, "worker_bluetooth").save().log()

        val isAirplaneModeEnabled =
            Settings.System.getInt(appContext.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0

        StatusLog("airplane", if (isAirplaneModeEnabled) "ON" else "OFF" , LogLevel.I, "worker_airplane").save().log()

        MainActivity.reportSubject = "bluetooth"
        MainActivity.reportStatus = bluetoothStatus
        return Result.success()
    }
}
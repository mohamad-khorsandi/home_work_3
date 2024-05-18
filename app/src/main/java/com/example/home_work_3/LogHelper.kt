package com.example.home_work_3

import android.text.format.DateFormat
import android.util.Log
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

val logSaver = LogSaver("logs.txt")

@Serializable
data class StatusLog(val subject:String, val status:String, val date: DateFormat) {
    private val msg: String = "$subject is $status"
    companion object {
        const val TAG = "MY_TAG"
    }
    fun w() {
        Log.w(TAG, msg)
    }

    fun i() {
        Log.i(TAG, msg)
    }

    fun d() {
        Log.d(TAG, msg)
    }
}

class LogSaver(fileName: String) {
    private var file: File = File(fileName)
    private lateinit var logList: MutableList<StatusLog>

    init {
        if (file.exists()) {
            logList = readFromFile()
        } else {
            file.createNewFile()
        }
    }

    fun addLog(statusLog: StatusLog) {
        logList.add(statusLog)
    }

    private fun writeToFile() {
        val jsonLogs = Json.encodeToString<MutableList<StatusLog>>(logList)
        file.writeText(jsonLogs)
    }

    private fun readFromFile(): MutableList<StatusLog>
    {
        return Json.decodeFromString<MutableList<StatusLog>>(file.readText())
    }
}

package com.example.home_work_3

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.runtime.State
import com.example.home_work_3.MainActivity.Companion.logSaver
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.File
import java.util.Date
import java.util.Locale
private val TAG = "MY_TAG"

data class StatusLog(val subject:String, val status:String, val level: LogLevel) {
    public val date: String = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ROOT).format(Date())!!

    public fun log() {
        when(level) {
            LogLevel.D -> Log.w(TAG, getMsg())
            LogLevel.I -> Log.i(TAG, getMsg())
            LogLevel.E -> Log.e(TAG, getMsg())
            else -> TODO()
        }
    }

    public fun getMsg(): String {
        return "$date $subject is $status"
    }

    fun save(): StatusLog {
        logSaver.addLog(this)
        return this
    }
}


enum class LogLevel {
    E,I,D
}

class LogSaver(fileName: String, val mainViewModel: MainViewModel) {
    private var file: File = File(fileName)
    private val gson = Gson()

    init {
        if (file.exists()) {
            mainViewModel.addAll(readFromFile())
        } else {
            file.createNewFile()
        }
    }

    fun addLog(statusLog: StatusLog) {
        mainViewModel.addLog(statusLog)
    }

    public fun writeToFile() {
        val arrayTpe = object : TypeToken<MutableList<StatusLog>>() {}.type
        val res: String = gson.toJson(mainViewModel.logs.value, arrayTpe)
        file.writeText(res)
    }

    private fun readFromFile(): MutableList<StatusLog>
    {
        if (file.readText().isEmpty()) {
            return mutableListOf()
        }
        val arrayType = object : TypeToken<List<StatusLog>>() {}.type
        val res: List<StatusLog> = gson.fromJson(file.readText(), arrayType)
        return res.toMutableList()
    }
}


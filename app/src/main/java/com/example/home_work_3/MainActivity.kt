package com.example.home_work_3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.home_work_3.ui.theme.Home_work_3Theme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    companion object {
        var reportSubject by mutableStateOf("")
        var reportStatus by mutableStateOf("")
        lateinit var logSaver: LogSaver
    }

    private val onStartWorker: () -> Unit = {
        val workManager = WorkManager.getInstance(applicationContext)
        val periodicRequest = PeriodicWorkRequest.Builder(
            StatusWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).build()
        workManager.enqueue(periodicRequest)
    }

    private val onStartClick: () -> Unit = {
        Intent(applicationContext, ChargeStatusService::class.java).also {
            it.action = ChargeStatusService.Actions.START.toString()
            startService(it)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Home_work_3Theme {
                MainPage()
            }
        }
    }

    @Preview
    @Composable
    fun MainPage() {
        val mainViewModel = MainViewModel()
        val logList by mainViewModel.logs.collectAsState()
        logSaver = LogSaver("/data/data/com.example.home_work_3/files/logs.txt", mainViewModel)

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                Text("CURRENT STATUS:")
                Text("$reportSubject is $reportStatus")
                Spacer(modifier = Modifier.height(30.dp))
                ControlSection(onStartClick, onStartWorker)
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "LOG HISTORY:", style = MaterialTheme.typography.titleLarge)
                for (log in logList) {
                    Text( log.getMsg())
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()
        logSaver.writeToFile()
    }
}


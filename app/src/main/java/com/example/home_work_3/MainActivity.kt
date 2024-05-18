package com.example.home_work_3

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.home_work_3.ui.theme.Home_work_3Theme

class MainActivity : ComponentActivity() {
    private val onStartClick: () -> Unit = {
        Intent(applicationContext, ChargeStatusService::class.java).also {
            it.action = ChargeStatusService.Actions.START.toString()
            startService(it)
        }
    }

    private val onStopClick: () -> Unit = {
        Intent(applicationContext, ChargeStatusService::class.java).also {
            it.action = ChargeStatusService.Actions.STOP.toString()
            startService(it)
        }
    }

    companion object {
        var reportSubject by mutableStateOf("")
        var reportStatus by mutableStateOf("")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        setContent {
            Home_work_3Theme {
                MainPage()
            }
        }
    }

    @Preview
    @Composable
    fun MainPage() {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                Text("$reportSubject $reportStatus")
                ControlSection(onStartClick, onStopClick)
            }
        }
    }
}
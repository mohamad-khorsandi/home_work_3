package com.example.home_work_3

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ControlSection(onServiceStart: () -> Unit, onWorkerStart: () -> Unit, modifier: Modifier) {
    Row {
        Button(onClick = onServiceStart) {
            Text(text = "start service")
        }
        Button(onClick = onWorkerStart) {
            Text(text = "start worker")
        }
    }
}
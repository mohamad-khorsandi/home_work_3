package com.example.home_work_3

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ControlSection(onWorkerStart: () -> Unit) {
    Row {
        Button(onClick = onWorkerStart) {
            Text(text = "start worker")
        }
    }
}
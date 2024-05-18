package com.example.home_work_3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LogSection(logList: List<StatusLog>, modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        Text(text = "LOG HISTORY:", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(logList.reversed()) {
                Text(text = it.getMsg())
            }
        }
    }

}
package com.example.home_work_3

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel {
    private var _logs = MutableStateFlow<List<StatusLog>>(emptyList())
    val logs = _logs.asStateFlow()

    fun addLog(log: StatusLog) {
        _logs.value += log
    }

    fun addAll(list: List<StatusLog>) {
        for(log in list ) {
            _logs.value += log
        }
    }
}
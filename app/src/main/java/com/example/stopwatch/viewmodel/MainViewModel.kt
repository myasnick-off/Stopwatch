package com.example.stopwatch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stopwatch.interactor.StateHolder
import com.example.stopwatch.utils.TimeFormatter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    timerStateHolder1: StateHolder,
    timerStateHolder2: StateHolder
    ) : ViewModel() {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    private var job1: Job? = null
    private var job2: Job? = null
    private val jobList: Array<Job?> = arrayOf(job1, job2)

    private val timerStateHolderList: Array<StateHolder> = arrayOf(
        timerStateHolder1, timerStateHolder2
    )

    private val _tickerList = arrayOf(
        MutableStateFlow(TimeFormatter.DEFAULT_TIME),
        MutableStateFlow(TimeFormatter.DEFAULT_TIME)
    )
    val tickerList: Array<StateFlow<String>> = arrayOf(_tickerList[0], _tickerList[1])

    fun start(index: Int) {
        if (jobList[index] == null) startJob(index)
        timerStateHolderList[index].start()
    }

    fun pause(index: Int) {
        timerStateHolderList[index].pause()
        stopJob(index)
    }

    fun stop(index: Int) {
        timerStateHolderList[index].stop()
        stopJob(index)
        clearValue(index)
    }

    private fun startJob(index: Int) {
        jobList[index] = scope.launch {
            while (isActive) {
                _tickerList[index].value = timerStateHolderList[index].convertTimeToString()
                delay(20)
            }
        }
    }

    private fun stopJob(index: Int) {
        jobList[index]?.cancel()
        jobList[index] = null
    }

    private fun clearValue(index: Int) {
        _tickerList[index].value = TimeFormatter.DEFAULT_TIME
    }
}
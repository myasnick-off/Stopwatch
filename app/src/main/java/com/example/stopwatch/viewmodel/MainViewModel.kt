package com.example.stopwatch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stopwatch.data.TimerProvider
import com.example.stopwatch.domain.TimePassedCalculator
import com.example.stopwatch.domain.TimerStateSetter
import com.example.stopwatch.repository.TimerStateHolder
import com.example.stopwatch.utils.TimeFormatter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {

    private val timerProvider = TimerProvider()
    private val timerStateHolder: TimerStateHolder = TimerStateHolder(
        TimerStateSetter(timerProvider, TimePassedCalculator(timerProvider)),
        TimePassedCalculator(timerProvider),
        TimeFormatter()
    )
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    private var job: Job? = null
    private var _ticker = MutableStateFlow(TimeFormatter.DEFAULT_TIME)
    val ticker: StateFlow<String> = _ticker

    fun start() {
        if (job == null) startJob()
        timerStateHolder.start()
    }

    fun pause() {
        timerStateHolder.pause()
        stopJob()
    }

    fun stop() {
        timerStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                _ticker.emit(timerStateHolder.convertTimeToString())
                delay(20)
            }
        }
    }

    private fun stopJob() {
        job?.cancel()
        job = null
    }

    private fun clearValue() {
        _ticker.value = TimeFormatter.DEFAULT_TIME
    }
}
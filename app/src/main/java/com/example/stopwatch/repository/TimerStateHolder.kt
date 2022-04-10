package com.example.stopwatch.repository

import com.example.stopwatch.utils.TimeFormatter
import com.example.stopwatch.domain.TimePassedCalculator
import com.example.stopwatch.domain.TimerStateSetter
import com.example.stopwatch.model.TimerState

class TimerStateHolder (
    private val timerStateSetter: TimerStateSetter,
    private val timePassedCalculator: TimePassedCalculator,
    private val timeFormatter: TimeFormatter,
) {
    var currentState: TimerState = TimerState.Paused(0)
        private set

    fun start() {
        currentState = timerStateSetter.setRunningState(currentState)
    }

    fun pause() {
        currentState = timerStateSetter.setPausedState(currentState)
    }

    fun stop() {
        currentState = TimerState.Paused(0)
    }

    fun convertTimeToString(): String {
        val passedTime = when (val currentState = currentState) {
            is TimerState.Paused -> currentState.passed
            is TimerState.Running -> timePassedCalculator.calculate(currentState)
        }
        return timeFormatter.format(passedTime)
    }
}
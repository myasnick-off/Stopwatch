package com.example.stopwatch.datastore

import com.example.stopwatch.domain.TimeFormatter
import com.example.stopwatch.domain.TimePassedCalculator
import com.example.stopwatch.domain.TimerStateCalculator
import com.example.stopwatch.model.TimerState

class TimerStateHolder (
    private val timerStateCalculator: TimerStateCalculator,
    private val timePassedCalculator: TimePassedCalculator,
    private val timeFormatter: TimeFormatter,
) {
    var currentState: TimerState = TimerState.Paused(0)
        private set

    fun start() {
        currentState = timerStateCalculator.setRunningState(currentState)
    }

    fun pause() {
        currentState = timerStateCalculator.setPausedState(currentState)
    }

    fun stop() {
        currentState = TimerState.Paused(0)
    }

    fun getStringTimeRepresentation(): String {
        val passedTime = when (val currentState = currentState) {
            is TimerState.Paused -> currentState.passed
            is TimerState.Running -> timePassedCalculator.calculate(currentState)
        }
        return timeFormatter.format(passedTime)
    }
}
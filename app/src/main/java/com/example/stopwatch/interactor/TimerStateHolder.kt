package com.example.stopwatch.interactor

import com.example.stopwatch.model.TimerState
import com.example.stopwatch.repository.StateSetter
import com.example.stopwatch.utils.TimeFormatter
import com.example.stopwatch.utils.TimePassedCalculator

class TimerStateHolder (
    private val timerStateSetter: StateSetter,
    private val timePassedCalculator: TimePassedCalculator,
    private val timeFormatter: TimeFormatter
) : StateHolder {
    var currentState: TimerState = TimerState.Paused(0)
        private set

    override fun start() {
        currentState = timerStateSetter.setRunningState(currentState)
    }

    override fun pause() {
        currentState = timerStateSetter.setPausedState(currentState)
    }

    override fun stop() {
        currentState = TimerState.Paused(0)
    }

    override fun convertTimeToString(): String {
        val passedTime = when (val currentState = currentState) {
            is TimerState.Paused -> currentState.passed
            is TimerState.Running -> timePassedCalculator.calculate(currentState)
        }
        return timeFormatter.format(passedTime)
    }
}
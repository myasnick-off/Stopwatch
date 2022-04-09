package com.example.stopwatch.domain

import com.example.stopwatch.model.TimerState

class TimerStateCalculator(
    private val timerProvider: TimerProvider,
    private val timePassedCalculator: TimePassedCalculator,
) {
    fun setRunningState(oldState: TimerState): TimerState.Running =
        when (oldState) {
            is TimerState.Running -> oldState
            is TimerState.Paused -> {
                TimerState.Running(startedAt = timerProvider.getCurrentTime(), passed = oldState.passed)
            }
        }

    fun setPausedState(oldState: TimerState): TimerState.Paused =
        when (oldState) {
            is TimerState.Running -> {
                val passedTime = timePassedCalculator.calculate(oldState)
                TimerState.Paused(passed = passedTime)
            }
            is TimerState.Paused -> oldState
        }
}
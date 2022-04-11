package com.example.stopwatch.repository

import com.example.stopwatch.datasource.ITimeProvider
import com.example.stopwatch.model.TimerState
import com.example.stopwatch.utils.TimePassedCalculator

class TimerStateSetter(
    private val timeProvider: ITimeProvider,
    private val timePassedCalculator: TimePassedCalculator,
) : StateSetter {
    override fun setRunningState(oldState: TimerState): TimerState.Running =
        when (oldState) {
            is TimerState.Running -> oldState
            is TimerState.Paused -> {
                TimerState.Running(
                    startedAt = timeProvider.getCurrentTime(),
                    passed = oldState.passed
                )
            }
        }

    override fun setPausedState(oldState: TimerState): TimerState.Paused =
        when (oldState) {
            is TimerState.Running -> {
                val passedTime = timePassedCalculator.calculate(oldState)
                TimerState.Paused(passed = passedTime)
            }
            is TimerState.Paused -> oldState
        }
}
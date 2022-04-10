package com.example.stopwatch.domain

import com.example.stopwatch.data.ITimerProvider
import com.example.stopwatch.model.TimerState

class TimePassedCalculator(private val timerProvider: ITimerProvider) {

    fun calculate(state: TimerState.Running): Long {
        val currentTime = timerProvider.getCurrentTime()
        val timePassedSinceStart = if (currentTime > state.startedAt) {
            currentTime - state.startedAt
        } else {
            0
        }
        return timePassedSinceStart + state.passed
    }
}
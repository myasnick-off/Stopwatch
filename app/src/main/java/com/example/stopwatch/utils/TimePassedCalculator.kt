package com.example.stopwatch.utils

import com.example.stopwatch.datasource.ITimeProvider
import com.example.stopwatch.model.TimerState

class TimePassedCalculator(private val timeProvider: ITimeProvider) {

    fun calculate(state: TimerState.Running): Long {
        val currentTime = timeProvider.getCurrentTime()
        val timePassedSinceStart = if (currentTime > state.startedAt) {
            currentTime - state.startedAt
        } else {
            0
        }
        return timePassedSinceStart + state.passed
    }
}
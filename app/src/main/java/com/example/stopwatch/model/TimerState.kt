package com.example.stopwatch.model

sealed class TimerState {
    data class Paused(val passed: Long) : TimerState()
    data class Running(val passed: Long, val startedAt: Long) : TimerState()
}
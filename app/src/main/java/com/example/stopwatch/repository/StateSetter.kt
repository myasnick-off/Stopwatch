package com.example.stopwatch.repository

import com.example.stopwatch.model.TimerState

interface StateSetter {
    fun setRunningState(oldState: TimerState): TimerState.Running
    fun setPausedState(oldState: TimerState): TimerState.Paused
}
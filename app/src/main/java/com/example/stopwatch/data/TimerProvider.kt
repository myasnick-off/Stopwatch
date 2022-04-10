package com.example.stopwatch.data

class TimerProvider : ITimerProvider {

    override fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }
}
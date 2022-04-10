package com.example.stopwatch.datasource

class TimeProvider : ITimeProvider {

    override fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }
}
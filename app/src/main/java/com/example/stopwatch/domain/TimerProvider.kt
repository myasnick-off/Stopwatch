package com.example.stopwatch.domain

interface TimerProvider {
    fun getCurrentTime(): Long
}
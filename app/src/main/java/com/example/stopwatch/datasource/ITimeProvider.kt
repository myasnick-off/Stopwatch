package com.example.stopwatch.datasource

interface ITimeProvider {
    fun getCurrentTime(): Long
}
package com.example.stopwatch.interactor

interface StateHolder {
    fun start()
    fun pause()
    fun stop()
    fun convertTimeToString(): String
}
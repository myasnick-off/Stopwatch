package com.example.stopwatch

import android.app.Application
import com.example.stopwatch.di.DI
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(DI.appModule, DI.mainModule)
        }
    }
}
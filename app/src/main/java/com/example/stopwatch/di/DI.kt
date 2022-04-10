package com.example.stopwatch.di

import com.example.stopwatch.datasource.ITimeProvider
import com.example.stopwatch.datasource.TimeProvider
import com.example.stopwatch.interactor.StateHolder
import com.example.stopwatch.interactor.TimerStateHolder
import com.example.stopwatch.repository.StateSetter
import com.example.stopwatch.repository.TimerStateSetter
import com.example.stopwatch.utils.TimeFormatter
import com.example.stopwatch.utils.TimePassedCalculator
import com.example.stopwatch.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DI {

    val appModule = module {
        single<ITimeProvider> { TimeProvider() }
        single<StateSetter> { TimerStateSetter(timeProvider = get(), timePassedCalculator = get()) }
        single { TimePassedCalculator(timeProvider = get()) }
        single { TimeFormatter() }
    }

    val mainModule = module {
        factory<StateHolder> {
            TimerStateHolder(timerStateSetter = get(), timePassedCalculator = get(), timeFormatter = get())
        }
        viewModel { MainViewModel(timerStateHolder = get()) }
    }
}
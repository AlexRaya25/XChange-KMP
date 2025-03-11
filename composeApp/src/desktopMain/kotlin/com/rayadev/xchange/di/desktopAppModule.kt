package com.rayadev.xchange.di

import com.rayadev.xchange.NumberFormatter
import com.rayadev.xchange.chart.DesktopChartDrawer
import com.rayadev.xchange.data.remote.HttpClientFactory
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.dsl.module

val desktopAppModule = module {
    single { NumberFormatter() }
    single { HttpClientFactory() }
    single { ExchangeViewModel(get(), DesktopChartDrawer()) }
}
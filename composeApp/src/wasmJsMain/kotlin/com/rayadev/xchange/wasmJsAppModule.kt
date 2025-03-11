package com.rayadev.xchange

import com.rayadev.xchange.chart.WebChartDrawer
import com.rayadev.xchange.data.remote.HttpClientFactory
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.dsl.module

val wasmJsAppModule = module {
    single { NumberFormatter() }
    single { HttpClientFactory() }
    single { ExchangeViewModel(get(), WebChartDrawer()) }
}
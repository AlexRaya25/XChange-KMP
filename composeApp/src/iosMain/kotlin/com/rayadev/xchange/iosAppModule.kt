package com.rayadev.xchange

import com.rayadev.xchange.chart.iosChartDrawer
import com.rayadev.xchange.data.remote.HttpClientFactory
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.dsl.module

val iosAppModule = module {

    single { HttpClientFactory() }
    single { ExchangeViewModel(get(), iosChartDrawer()) }
}
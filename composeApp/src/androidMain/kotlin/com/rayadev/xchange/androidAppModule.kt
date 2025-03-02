package com.rayadev.xchange

import com.rayadev.xchange.chart.AndroidChartDrawer
import com.rayadev.xchange.data.remote.HttpClientFactory
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidAppModule = module {
    single { NumberFormatter() }
    single { HttpClientFactory() }
    viewModel { ExchangeViewModel(get(), AndroidChartDrawer()) }
}
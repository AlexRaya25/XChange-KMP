package com.rayadev.xchange

import com.rayadev.xchange.chart.AndroidChartDrawer
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidAppModule = module {
    viewModel { ExchangeViewModel(get(), AndroidChartDrawer()) }
}
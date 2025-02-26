package com.rayadev.xchange

import com.rayadev.xchange.chart.DesktopChartDrawer
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.dsl.module

val desktopAppModule = module {
    single { ExchangeViewModel(get(), DesktopChartDrawer()) }
}
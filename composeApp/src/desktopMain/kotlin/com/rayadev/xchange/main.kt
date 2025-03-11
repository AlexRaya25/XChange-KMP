package com.rayadev.xchange

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import com.xchange.di.*
import com.rayadev.xchange.di.desktopAppModule
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.core.context.GlobalContext

fun main() = application {
    startKoin {
        modules(appModule + desktopAppModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "XChange",
    ) {
        val viewModel = GlobalContext.get().get<ExchangeViewModel>()
        App(viewModel)
    }
}

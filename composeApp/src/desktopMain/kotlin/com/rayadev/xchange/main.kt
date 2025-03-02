package com.rayadev.xchange

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import com.xchange.di.*
import javax.swing.SwingUtilities
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
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
        //ChartScreen()
        //WebViewExample()
    }
}
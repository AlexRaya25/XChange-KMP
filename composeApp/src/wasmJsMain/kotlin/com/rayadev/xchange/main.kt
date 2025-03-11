package com.rayadev.xchange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import com.xchange.di.appModule
import kotlinx.browser.document
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(appModule + wasmJsAppModule)
    }

    ComposeViewport(document.body!!) {
        val viewModel = GlobalContext.get().get<ExchangeViewModel>()
        Box(
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                .background(MaterialTheme.colorScheme.error)
        ) {
            App(viewModel)
        }
    }

}
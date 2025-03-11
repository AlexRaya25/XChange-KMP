package com.rayadev.xchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rayadev.xchange.di.androidAppModule
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.core.context.startKoin
import com.xchange.di.appModule
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(appModule + androidAppModule)
        }

        setContent {
            val viewModel: ExchangeViewModel = koinViewModel()
            App(viewModel)
        }
    }
}
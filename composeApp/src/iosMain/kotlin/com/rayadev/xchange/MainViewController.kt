package com.rayadev.xchange

import androidx.compose.ui.window.ComposeUIViewController
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import com.rayadev.xchange.App
import com.rayadev.xchange.chart.iosChartDrawer
import com.rayadev.xchange.data.remote.HttpClientFactory
import com.xchange.data.remote.FrankfurterApi
import com.xchange.di.ExchangeService
import com.xchange.di.appModule
import io.ktor.http.parametersOf
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController {
    DependenciesProviderHelper().initKoin() // Llamamos a la inicialización de Koin
    // Obtén el ViewModel utilizando Koin
    val viewModel: ExchangeViewModel = DependenciesProviderHelper.koin.get() // Usamos nuestro método get()

    // Llama a la función Composable que recibe el ViewModel
    App(viewModel)
}


class DependenciesProviderHelper {
    private var koinInitialized = false

    fun initKoin() {
        if (!koinInitialized) {
            val iosModule = module {
                single { NumberFormatter() }
                single { HttpClientFactory() }
                single { ExchangeViewModel(get(), iosChartDrawer()) }
            }

            val instance = startKoin {
                modules(appModule + iosModule) // Asegúrate de incluir tus módulos base
            }

            // Guardamos el contexto de Koin
            koin = instance.koin
            koinInitialized = true
        }
    }

    companion object {
        lateinit var koin: Koin
    }
}



@OptIn(BetaInteropApi::class)
fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null, null)
}

@OptIn(BetaInteropApi::class)
fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}

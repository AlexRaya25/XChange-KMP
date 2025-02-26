package com.rayadev.xchange

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import com.xchange.di.*
import javafx.embed.swing.JFXPanel
import javafx.scene.web.WebView
import javax.swing.SwingUtilities
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import javafx.application.Platform
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.koin.core.context.GlobalContext

fun main() = application {
    startKoin {
        modules(appModule + desktopAppModule)
    }

    Platform.startup {
        // JavaFX is now initialized
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

@Composable
fun WebViewExample() {
    Text("WebView Example")
    SwingPanel(
        modifier = Modifier.fillMaxSize(),
        factory = {
            // Crear el JFXPanel para usar componentes JavaFX
            val jfxPanel = JFXPanel()
            SwingUtilities.invokeLater {
                // Inicializa WebView en el hilo de JavaFX
                val webView = WebView()
                val htmlContent = """
                    <html>
                        <head>
                            <script src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script>
                        </head>
                        <body>
                            <div id="chart" style="width: 100%; height: 100%;"></div>
                            <script>
                                var chart = echarts.init(document.getElementById('chart'));
                                var option = {
                                    tooltip: { trigger: 'axis' },
                                    xAxis: { 
                                        type: 'category', 
                                        data: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"] 
                                    },
                                    yAxis: { type: 'value' },
                                    series: [{
                                        name: 'Exchange Rate',
                                        type: 'line',
                                        smooth: true,
                                        areaStyle: {},
                                        data: [120, 132, 101, 134, 90, 230, 210]
                                    }]
                                };
                                chart.setOption(option);

                                chart.on('mouseover', function (params) {
                                    if (params.seriesType === 'line') {
                                        window.android.onValueSelected(params.data, params.name);
                                    }
                                });

                                chart.on('mouseout', function () {
                                    window.android.onNothingSelected();
                                });
                            </script>
                        </body>
                    </html>
                """
                // Cargar el HTML dentro del WebView
                webView.engine.loadContent(htmlContent)
                jfxPanel.scene = javafx.scene.Scene(webView)
            }
            jfxPanel
        }
    )
}
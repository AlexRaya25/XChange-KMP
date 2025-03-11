package com.rayadev.xchange.chart

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class WebChartDrawer : ChartDrawer {
    @Composable
    override fun DrawLineChart(
        data: Map<String, Double>,
        selectedRange: String,
        onRangeChange: (String) -> Unit
    ) {
//        val data2 = mapOf(
//            "2023-01-01" to 100.0,
//            "2023-01-02" to 120.0,
//            "2023-01-03" to 90.0,
//            "2023-01-04" to 110.0,
//            "2023-01-05" to 150.0
//        )
//
//        var filteredEntries by remember { mutableStateOf<List<DefaultPoint<Float, Float>>>(emptyList()) }
//        var xLabels by remember { mutableStateOf<List<String>>(emptyList()) }
//
//        // Usamos un rango predeterminado
//        LaunchedEffect(data2) {
//            val sortedEntries = data2.entries.sortedBy { it.key }
//
//            val filteredData = sortedEntries.mapIndexed { index, entry ->
//                DefaultPoint(index.toFloat(), entry.value.toFloat())
//            }
//
//            filteredEntries = filteredData
//            xLabels = sortedEntries.map { it.key }
//        }
//
//        KoalaPlotTheme(
//            axis = KoalaPlotTheme.axis.copy(
//                color = Color.Black,
//                minorGridlineStyle = null
//            )
//        ) {
//            if (filteredEntries.isNotEmpty() && xLabels.isNotEmpty()) {
//                XYGraph(
//                    xAxisModel = rememberFloatLinearAxisModel(filteredEntries.autoScaleXRange()),
//                    yAxisModel = rememberFloatLinearAxisModel(filteredEntries.autoScaleYRange()),
//                    xAxisLabels = { index ->
//                        if (index.toInt() in xLabels.indices) {
//                            xLabels[index.toInt()]
//                        } else {
//                            ""
//                        }
//                    }
//                ) {
//                    AreaPlot(
//                        data = filteredEntries,
//                        lineStyle = LineStyle(
//                            brush = SolidColor(Color(0xFF007BFF)),
//                            strokeWidth = 2.dp
//                        ),
//                        areaStyle = AreaStyle(
//                            brush = SolidColor(Color(0x22007BFF)),
//                            alpha = 1.0f
//                        ),
//                        areaBaseline = AreaBaseline.ConstantLine(0f)
//                    )
//                }
//            }
//        }
        Text("hola que tal")
    }
}
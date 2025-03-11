package com.rayadev.xchange.chart

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.line.AreaBaseline
import io.github.koalaplot.core.line.AreaPlot
import io.github.koalaplot.core.style.AreaStyle
import io.github.koalaplot.core.style.KoalaPlotTheme
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.DefaultPoint
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.autoScaleXRange
import io.github.koalaplot.core.xygraph.autoScaleYRange
import io.github.koalaplot.core.xygraph.rememberFloatLinearAxisModel
import org.jetbrains.compose.resources.stringResource
import xchange.composeapp.generated.resources.Res
import xchange.composeapp.generated.resources.text_1_month
import xchange.composeapp.generated.resources.text_1_year
import xchange.composeapp.generated.resources.text_5_days
import xchange.composeapp.generated.resources.text_6_month
import java.text.SimpleDateFormat
import java.util.*

class DesktopChartDrawer : ChartDrawer {

    @Suppress("DefaultLocale")
    @OptIn(ExperimentalKoalaPlotApi::class)
    @Composable
    override fun DrawLineChart(
        data: Map<String, Double>,
        selectedRange: String,
        onRangeChange: (String) -> Unit
    ) {
        if (data.isEmpty()) {
            return
        }

        var filteredEntries by remember { mutableStateOf<List<DefaultPoint<Float, Float>>>(emptyList()) }
        var xLabels by remember { mutableStateOf<List<String>>(emptyList()) }

        val text5Days = stringResource(Res.string.text_5_days)
        val text1Month = stringResource(Res.string.text_1_month)
        val text6Month = stringResource(Res.string.text_6_month)
        val text1Year = stringResource(Res.string.text_1_year)

        LaunchedEffect(selectedRange, data) {
            val sortedEntries = data.entries.sortedBy {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.key) ?: Date()
            }

            val (filteredData, newXLabels) = when (selectedRange) {
                text5Days -> sortedEntries.takeLast(5) to generateXLabels(sortedEntries.takeLast(5))
                text1Month -> sortedEntries.takeLast(30) to generateXLabels(sortedEntries.takeLast(30))
                text6Month -> sortedEntries.takeLast(180) to generateXLabels(sortedEntries.takeLast(180))
                text1Year -> sortedEntries.takeLast(365) to generateXLabels(sortedEntries.takeLast(365))
                else -> sortedEntries.takeLast(5) to generateXLabels(sortedEntries.takeLast(5))
            }

            if (filteredData.isNotEmpty()) {
                filteredEntries = filteredData.mapIndexed { index, entry ->
                    DefaultPoint(index.toFloat(), "%.3f".format(Locale.US, entry.value).toFloat())
                }
            }

            xLabels = if (newXLabels.size == filteredEntries.size) {
                newXLabels
            } else {
                List(filteredEntries.size) { "" }
            }
        }

        KoalaPlotTheme(
            axis = KoalaPlotTheme.axis.copy(
                color = Color.Black,
                minorGridlineStyle = null
            )
        ) {
            if (filteredEntries.isNotEmpty() && xLabels.isNotEmpty()) {
                XYGraph(
                    xAxisModel = rememberFloatLinearAxisModel(filteredEntries.autoScaleXRange()),
                    yAxisModel = rememberFloatLinearAxisModel(filteredEntries.autoScaleYRange()),
                    xAxisLabels = { index ->
                        if (index.toInt() in xLabels.indices) {
                            xLabels[index.toInt()]
                        } else {
                            ""
                        }
                    }
                ) {
                    AreaPlot(
                        data = filteredEntries,
                        lineStyle = LineStyle(
                            brush = SolidColor(Color(0xFF007BFF)),
                            strokeWidth = 1.dp
                        ),
                        areaStyle = AreaStyle(
                            brush = SolidColor(Color(0x22007BFF)),
                            alpha = 1.0f
                        ),
                        areaBaseline = AreaBaseline.ConstantLine(0f)
                    )
                }
            }
        }
    }

    private fun generateXLabels(filteredData: List<Map.Entry<String, Double>>): List<String> {
        val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault())
        return filteredData.map { entry ->
            dateFormat.format(
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(entry.key) ?: Date()
            )
        }
    }
}

package com.rayadev.xchange.chart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

class DesktopChartDrawer : ChartDrawer {

    @Composable
    override fun DrawLineChart(
        data: Map<String, Double>,
        selectedRange: String,
        onRangeChange: (String) -> Unit
    ) {
        if (data.isEmpty()) {
            return
        }

        var selectedPrice by remember { mutableStateOf("") }
        var selectedDate by remember { mutableStateOf("") }
        var showPriceBox by remember { mutableStateOf(false) }

        val sortedEntries = data.entries.sortedBy {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.key) ?: Date()
        }

        val (filteredData, xLabels) = when (selectedRange) {
            "5 días" -> sortedEntries.takeLast(5) to generateXLabels(5, 1)
            "1 mes" -> sortedEntries.takeLast(30) to generateXLabels(30, 1)
            "6 meses" -> sortedEntries.takeLast(180) to generateXLabels(180, 1)
            "1 año" -> sortedEntries.takeLast(365) to generateXLabels(365, 1)
            else -> sortedEntries.takeLast(5) to generateXLabels(5, 1)
        }

        val dataValues = filteredData.map { it.value }
        val dataLabels = xLabels

        val echartsHtml = """
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
                            data: ${dataLabels.map { "\"$it\"" }} 
                        },
                        yAxis: { type: 'value' },
                        series: [{
                            name: 'Exchange Rate',
                            type: 'line',
                            smooth: true,
                            areaStyle: {},
                            data: $dataValues
                        }]
                    };
                    chart.setOption(option);

                    chart.on('mouseover', function (params) {
                        if (params.seriesType === 'line') {
                            window.bridge.onValueSelected(params.data, params.name);
                        }
                    });

                    chart.on('mouseout', function () {
                        window.bridge.onNothingSelected();
                    });
                </script>
            </body>
        </html>
        """.trimIndent()

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            }

            if (showPriceBox) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Card(
                        modifier = Modifier.width(90.dp).height(50.dp),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = selectedPrice, style = MaterialTheme.typography.bodyMedium)
                            Text(text = selectedDate, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }

    private fun generateXLabels(days: Int, step: Int): List<String> {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault())
        return (0 until days step step).map {
            calendar.add(Calendar.DAY_OF_YEAR, -step)
            dateFormat.format(calendar.time)
        }.reversed()
    }
}
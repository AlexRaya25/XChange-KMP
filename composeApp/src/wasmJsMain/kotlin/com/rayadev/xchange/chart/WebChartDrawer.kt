package com.rayadev.xchange.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json

//fun drawChart() {
//    // Use a constant string expression for the `js()` function
//    js(
//        """
//        new Chart(document.getElementById('chartCanvas').getContext('2d'), {
//        type: 'bar',
//        data: {
//            labels: ['A', 'B', 'C'],
//            datasets: [{
//                label: 'Sample Data',
//                data: [10, 20, 30],
//                backgroundColor: 'rgba(54, 162, 235, 0.5)'
//            }]
//        }
//    });
//        """
//    )
//}

@OptIn(ExperimentalJsExport::class)
class WebChartDrawer : ChartDrawer {
    @Composable
    override fun DrawLineChart(
        data: Map<String, Double>,
        selectedRange: String,
        onRangeChange: (String) -> Unit
    ) {
//        LaunchedEffect(Unit) {
//            drawChart()
//        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
        ) {
            Text("Aquí aparecerá el gráfico")
        }
    }
}
package com.rayadev.xchange.chart

import androidx.compose.runtime.Composable

interface ChartDrawer {
    @Composable
    fun DrawLineChart(data: Map<String, Double>, selectedRange: String, onRangeChange: (String) -> Unit)
}

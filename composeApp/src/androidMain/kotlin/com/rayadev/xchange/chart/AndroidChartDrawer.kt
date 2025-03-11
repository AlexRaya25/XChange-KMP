package com.rayadev.xchange.chart

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import org.jetbrains.compose.resources.stringResource
import xchange.composeapp.generated.resources.Res
import xchange.composeapp.generated.resources.text_1_month
import xchange.composeapp.generated.resources.text_1_year
import xchange.composeapp.generated.resources.text_5_days
import xchange.composeapp.generated.resources.text_6_month
import xchange.composeapp.generated.resources.text_exchange_rate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AndroidChartDrawer : ChartDrawer {

    @SuppressLint("UseOfNonLambdaOffsetOverload")
    @Composable
    override fun DrawLineChart(
        data: Map<String, Double>,
        selectedRange: String,
        onRangeChange: (String) -> Unit
    ) {
        if (data.isEmpty()) {
            //Log.d("ChartDrawer", "No hay datos para mostrar en el gráfico")
            return
        }

        var selectedPrice by remember { mutableStateOf("") }
        var selectedDate by remember { mutableStateOf("") }
        var showPriceBox by remember { mutableStateOf(false) }
        var selectedX by remember { mutableFloatStateOf(0f) }
        var selectedY by remember { mutableFloatStateOf(0f) }

        var filteredEntries by remember { mutableStateOf<List<Entry>>(emptyList()) }
        var xLabels by remember { mutableStateOf<List<String>>(emptyList()) }

        val text5Days = stringResource(Res.string.text_5_days)
        val text1Month = stringResource(Res.string.text_1_month)
        val text6Month = stringResource(Res.string.text_6_month)
        val text1Year = stringResource(Res.string.text_1_year)

        LaunchedEffect(selectedRange, data) {
            val sortedEntries = data.entries.sortedBy {
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).parse(it.key) ?: Date()
            }

            val (filteredData, newXLabels) = when (selectedRange) {
                text5Days -> sortedEntries.takeLast(5) to generateXLabels(5, 1)
                text1Month -> sortedEntries.takeLast(30) to generateXLabels(30, 1)
                text6Month -> sortedEntries.takeLast(180) to generateXLabels(180, 1)
                text1Year -> sortedEntries.takeLast(365) to generateXLabels(365, 1)
                else -> sortedEntries.takeLast(5) to generateXLabels(5, 1)
            }

            filteredEntries = filteredData.mapIndexed { index, entry ->
                Entry(index.toFloat(), entry.value.toFloat())
            }
            xLabels = newXLabels
        }

        val dataSet = LineDataSet(filteredEntries, stringResource(Res.string.text_exchange_rate)).apply {
            color = 0xFF007BFF.toInt()
            valueTextColor = Color.BLACK
            lineWidth = 2f
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillColor = 0x22007BFF
            setDrawCircles(false)
            cubicIntensity = 0.2f
        }

        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                factory = { context ->
                    LineChart(context).apply {
                        setTouchEnabled(true)
                        setDragEnabled(true)
                        setScaleEnabled(false)

                        this.data = LineData(dataSet)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            textSize = 12f
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return xLabels.getOrNull(value.toInt()) ?: ""
                                }
                            }

                            val labelCount = if (xLabels.size < 10) xLabels.size else 10
                            setLabelCount(labelCount, true)
                        }

                        axisLeft.textSize = 12f
                        axisRight.isEnabled = false
                        description.isEnabled = false
                        legend.isEnabled = false

                        setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                            override fun onValueSelected(e: Entry?, h: Highlight?) {
                                e?.let {
                                    selectedDate = xLabels.getOrNull(e.x.toInt()) ?: ""
                                    selectedPrice = "${e.y}"
                                    selectedX = h?.xPx ?: 0f
                                    selectedY = h?.yPx ?: 0f
                                    showPriceBox = true
                                }
                            }

                            override fun onNothingSelected() {
                                showPriceBox = false
                            }
                        })
                    }
                },
                update = { chart ->
                    chart.data = LineData(dataSet)
                    chart.notifyDataSetChanged()
                    chart.invalidate()
                }
            )

            if (showPriceBox) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Card(
                        modifier = Modifier
                            .width(90.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.9f
                            )
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = selectedPrice,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = selectedDate,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
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

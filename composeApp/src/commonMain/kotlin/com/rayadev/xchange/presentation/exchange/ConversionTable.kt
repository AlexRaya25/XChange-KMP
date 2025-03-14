package com.rayadev.xchange.presentation.exchange

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rayadev.xchange.NumberFormatter
import org.jetbrains.compose.resources.stringResource
import xchange.composeapp.generated.resources.Res
import xchange.composeapp.generated.resources.conversion_table

@Composable
fun ConversionTable(
    currency1: String,
    currency2: String,
    exchangeRates: Double,
    currencies: Map<String, String>?
) {
    val amounts = listOf(1, 5, 10, 50, 100, 500, 1000, 5000, 10000)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentSize(Alignment.Center)
            .widthIn(max = 800.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.conversion_table),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${currencies?.get(currency1) ?: currency1}",
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onSurface),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${currencies?.get(currency2) ?: currency2}",
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onSurface),
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }

                val numberFormatter = NumberFormatter.getInstance()

                amounts.forEach { amount ->
                    val convertedAmount = amount * exchangeRates
                    val formattedAmount = numberFormatter.format(convertedAmount)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$amount $currency1",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = formattedAmount.toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
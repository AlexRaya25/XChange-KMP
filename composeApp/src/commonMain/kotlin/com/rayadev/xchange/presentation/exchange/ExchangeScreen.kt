package com.rayadev.xchange.presentation.exchange

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import xchange.composeapp.generated.resources.Res
import xchange.composeapp.generated.resources.ic_swap

@Composable
fun ExchangeScreen(sharedViewModel: ExchangeViewModel, paddingValues: PaddingValues) {
    var amount1 by remember { mutableStateOf("1") }
    var currency1 by remember { mutableStateOf("USD") }
    var currency2 by remember { mutableStateOf("EUR") }
    val resultTop by sharedViewModel.resultTop.collectAsState()
    val resultBottom by sharedViewModel.resultBottom.collectAsState()
    val amount2 by sharedViewModel.amount2.collectAsState()
    val currencies by sharedViewModel.currencies.collectAsState()
    var isSwapping by remember { mutableStateOf(false) }
    val selectedRange by sharedViewModel.selectedRange.collectAsState()
    val selectedCurrencyName1 = currencies?.get(currency1) ?: "United States Dollar"
    val selectedCurrencyName2 = currencies?.get(currency2) ?: "Euro"

    val amountFirst by sharedViewModel.amountFirst.collectAsState()

    val ranges = listOf("5 días", "1 mes", "6 meses", "1 año")

    // Conversión en tiempo real
    LaunchedEffect(amount1, currency1, currency2) {
        val amountValue = amount1.toDoubleOrNull() ?: 0.0
        if (amountValue > 0) {
            sharedViewModel.convertCurrency(
                currency1,
                currency2,
                selectedCurrencyName1,
                selectedCurrencyName2,
                amountValue
            )
        }
    }

    LaunchedEffect(currency1, currency2) {
        sharedViewModel.fetchExchangeRates(currency1, currency2)
        sharedViewModel.oneConvertCurrency(currency1, currency2)
    }

    LaunchedEffect(isSwapping) {
        if (isSwapping) {
            delay(300) // Esperar la animación de salida
            // Intercambiar valores
            val tempCurrency = currency1
            currency1 = currency2
            currency2 = tempCurrency
            amount1 = amount2
            delay(500)
            isSwapping = false
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = resultTop,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = resultBottom,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Box(modifier = Modifier.height(200.dp)) {
                AnimatedVisibility(
                    visible = !isSwapping,
                    exit = slideOutHorizontally(
                        targetOffsetX = { -it * 2 }, // Se va completamente a la izquierda
                        animationSpec = tween(durationMillis = 600)
                    ),
                    enter = slideInHorizontally(
                        initialOffsetX = { it }, // Aparece desde la derecha
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    CurrencyCard(
                        amount = amount1,
                        onAmountChange = { amount1 = it },
                        currency = currency1,
                        onCurrencyChange = { currency1 = it },
                        currencies = currencies,
                        label = "De"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(6.dp))
        }

        item {
            // Botón de intercambio
            IconButton(
                onClick = { isSwapping = true },
                modifier = Modifier
                    .size(48.dp)
                    .padding(vertical = 8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_swap),
                    contentDescription = "Intercambiar"
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(6.dp))
        }

        item {
            Box(modifier = Modifier.height(200.dp)) { // Mantiene el espacio aunque la tarjeta desaparezca
                AnimatedVisibility(
                    visible = !isSwapping,
                    exit = slideOutHorizontally(
                        targetOffsetX = { it * 2 }, // Se va completamente a la derecha
                        animationSpec = tween(durationMillis = 600)
                    ),
                    enter = slideInHorizontally(
                        initialOffsetX = { -it }, // Aparece desde la izquierda
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    CurrencyCard(
                        amount = amount2,
                        onAmountChange = {},
                        currency = currency2,
                        onCurrencyChange = { currency2 = it },
                        currencies = currencies,
                        label = "A",
                        isEditable = false
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ConversionTable(currency1, currency2, amountFirst, currencies)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Para dejar algo de espacio en los laterales
                horizontalArrangement = Arrangement.Center, // Centrar el contenido horizontalmente
                verticalAlignment = Alignment.CenterVertically // Asegura que el texto y la línea estén alineados verticalmente
            ) {
                Text(
                    text = "De ",
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface // Color de texto normal
                )
                Text(
                    text = selectedCurrencyName1,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary // Color destacado para la primera moneda
                )
                Text(
                    text = " a ",
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface // Color de texto normal
                )
                Text(
                    text = selectedCurrencyName2,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary // Color destacado para la segunda moneda
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y la línea de separación

            // Línea de separación
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp) // Altura de la línea
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) // Color de la línea, con opacidad
            )

            Spacer(modifier = Modifier.height(12.dp)) // Espacio después de la línea de separación
        }


        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ranges.forEach { range ->
                    Text(
                        text = range,
                        modifier = Modifier
                            .clickable { sharedViewModel.updateSelectedRange(range) } // Actualiza el rango seleccionado
                            .padding(8.dp),
                        color = if (selectedRange == range) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Aquí se ajusta el gráfico para que no haga scroll
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(300.dp)
            ) {
                sharedViewModel.DrawChart(selectedRange) { range ->
                    sharedViewModel.updateSelectedRange(range)
                    sharedViewModel.fetchExchangeRates(currency1, currency2)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}



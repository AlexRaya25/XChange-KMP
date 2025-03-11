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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
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
import org.jetbrains.compose.resources.stringResource
import xchange.composeapp.generated.resources.Res
import xchange.composeapp.generated.resources.app_name
import xchange.composeapp.generated.resources.ic_swap
import xchange.composeapp.generated.resources.text_1_month
import xchange.composeapp.generated.resources.text_1_year
import xchange.composeapp.generated.resources.text_5_days
import xchange.composeapp.generated.resources.text_6_month
import xchange.composeapp.generated.resources.text_exchange
import xchange.composeapp.generated.resources.text_from
import xchange.composeapp.generated.resources.text_to

@Composable
fun ExchangeScreen(sharedViewModel: ExchangeViewModel, paddingValues: PaddingValues, reloadTrigger: Int) {

    val currencies by sharedViewModel.currencies.collectAsState()
    val resultTop by sharedViewModel.resultTop.collectAsState()
    val resultBottom by sharedViewModel.resultBottom.collectAsState()
    val amount2 by sharedViewModel.amount2.collectAsState()
    val selectedRange by sharedViewModel.selectedRange.collectAsState()
    val amountFirst by sharedViewModel.amountFirst.collectAsState()

    var amount1 by remember { mutableStateOf("1") }
    var currency1 by remember { mutableStateOf("USD") }
    var currency2 by remember { mutableStateOf("EUR") }
    val selectedCurrencyName1 = currencies?.get(currency1) ?: "United States Dollar"
    val selectedCurrencyName2 = currencies?.get(currency2) ?: "Euro"

    var isSwapping by remember { mutableStateOf(false) }

    val ranges = listOf(
        stringResource(Res.string.text_5_days),
        stringResource(Res.string.text_1_month),
        stringResource(Res.string.text_6_month),
        stringResource(Res.string.text_1_year)
    )

    LaunchedEffect(reloadTrigger, amount1, currency1, currency2) {

        sharedViewModel.fetchExchangeRates(currency1, currency2)
        sharedViewModel.oneConvertCurrency(currency1, currency2)
        sharedViewModel.refreshCurrencies()

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

    LaunchedEffect(isSwapping) {
        if (isSwapping) {
            delay(300)
            val tempCurrency = currency1
            currency1 = currency2
            currency2 = tempCurrency
            amount1 = amount2
            delay(500)
            isSwapping = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = resultTop,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = resultBottom,
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Box(modifier = Modifier.height(200.dp)) {
                    AnimatedVisibility(
                        visible = !isSwapping,
                        exit = slideOutHorizontally(
                            targetOffsetX = { -it * 2 },
                            animationSpec = tween(durationMillis = 600)
                        ),
                        enter = slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 300)
                        )
                    ) {
                        CurrencyCard(
                            amount = amount1,
                            onAmountChange = { newAmount ->
                                amount1 = newAmount
                            },
                            currency = currency1,
                            onCurrencyChange = { newCurrency ->
                                currency1 = newCurrency
                            },
                            currencies = currencies,
                            label = stringResource(Res.string.text_from)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(6.dp)) }

            item {
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
                        contentDescription = stringResource(Res.string.text_exchange)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(6.dp)) }

            item {
                Box(modifier = Modifier.height(200.dp)) {
                    AnimatedVisibility(
                        visible = !isSwapping,
                        exit = slideOutHorizontally(
                            targetOffsetX = { it * 2 },
                            animationSpec = tween(durationMillis = 600)
                        ),
                        enter = slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(durationMillis = 300)
                        )
                    ) {
                        CurrencyCard(
                            amount = amount2,
                            onAmountChange = { },
                            currency = currency2,
                            onCurrencyChange = { newCurrency ->
                                currency2 = newCurrency
                            },
                            currencies = currencies,
                            label = stringResource(Res.string.text_to),
                            isEditable = false
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                ConversionTable(currency1, currency2, amountFirst, currencies)
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.text_from),
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                    Text(
                        text = selectedCurrencyName1,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = stringResource(Res.string.text_to),
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                    Text(
                        text = selectedCurrencyName2,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                )

                Spacer(modifier = Modifier.height(12.dp))
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
                                .clickable { sharedViewModel.updateSelectedRange(range) }
                                .padding(8.dp),
                            color = if (selectedRange == range) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

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

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
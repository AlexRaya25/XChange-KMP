package com.rayadev.xchange.presentation.exchange

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.xchange.NumberFormatter
import com.rayadev.xchange.chart.ChartDrawer
import com.xchange.di.ExchangeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.rayadev.xchange.di.Result
import org.jetbrains.compose.resources.stringResource
import xchange.composeapp.generated.resources.Res
import xchange.composeapp.generated.resources.load_graph

class ExchangeViewModel(private val exchangeService: ExchangeService, private val chartDrawer: ChartDrawer) : ViewModel() {

    private val _currencies = MutableStateFlow<Map<String, String>?>(null)
    val currencies: StateFlow<Map<String, String>?> = _currencies

    private val _resultTop = MutableStateFlow("")
    val resultTop: StateFlow<String> = _resultTop

    private val _resultBottom = MutableStateFlow("")
    val resultBottom: StateFlow<String> = _resultBottom

    private val _amountFirst = MutableStateFlow(1.00)
    val amountFirst: StateFlow<Double> = _amountFirst

    private val _amount2 = MutableStateFlow("")
    val amount2: StateFlow<String> = _amount2

    private val _exchangeRates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val exchangeRates: StateFlow<Map<String, Double>> = _exchangeRates

    private val _selectedRange = MutableStateFlow("")
    val selectedRange: StateFlow<String> = _selectedRange

    init {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            when (val result = exchangeService.getCurrencies()) {
                is Result.Success -> {
                    _currencies.value = result.value
                }
                is Result.Failure -> {
                    handleApiError(result.exception)
                    _currencies.value = emptyMap()
                }
            }
        }
    }

    fun refreshCurrencies() {
        loadCurrencies()
    }

    fun fetchExchangeRates(from: String, to: String) {
        viewModelScope.launch {
            when (val result = exchangeService.getExchangeRates(from, to)) {
                is Result.Success -> {
                    _exchangeRates.value = result.value
                }
                is Result.Failure -> {
                    handleApiError(result.exception)
                }
            }
        }
    }

    fun updateSelectedRange(range: String) {
        _selectedRange.value = range
    }

    @Composable
    fun DrawChart(selectedRange: String, onRangeChange: (String) -> Unit) {
        val exchangeRatesState by exchangeRates.collectAsState()

        if (exchangeRatesState.isNotEmpty()) {
            chartDrawer.DrawLineChart(
                data = exchangeRatesState,
                selectedRange = selectedRange,
                onRangeChange = onRangeChange
            )
        } else {
            Text(stringResource(Res.string.load_graph))
        }
    }

    fun oneConvertCurrency(from: String, to: String) {
        viewModelScope.launch {
            when (val result = exchangeService.convert(1.00, from, to)) {
                is Result.Success -> {
                    val formattedAmount = formatNumber(result.value).replace(",", "")
                    _amountFirst.value = formattedAmount.toDouble()
                }
                is Result.Failure -> {}
            }
        }
    }

    fun convertCurrency(from: String, to: String, name1: String, name2: String, amount: Double) {
        viewModelScope.launch {
            when (val result = exchangeService.convert(amount, from, to)) {
                is Result.Success -> {
                    val formattedAmount = formatNumber(result.value).replace(",", "")
                    _resultTop.value = "$amount $name1 = "
                    _resultBottom.value = "$formattedAmount $name2"
                    _amount2.value = formattedAmount
                }
                is Result.Failure -> {
                    _resultTop.value = "Error"
                    _resultBottom.value = result.exception.message ?: "Unknown Error"
                    _amount2.value = ""
                }
            }
        }
    }


    fun formatNumber(value: Double): String {
        return NumberFormatter.getInstance().format(value)
    }


    private fun handleApiError(exception: Throwable) {
        _exchangeRates.value = emptyMap()
        _resultTop.value = "Error al cargar datos"
        _resultBottom.value = exception.message ?: "Error desconocido"
    }
}

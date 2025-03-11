package com.xchange.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import com.rayadev.xchange.di.Result
import org.jetbrains.compose.resources.stringResource

class FrankfurterApi(private val client: HttpClient) {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        var attempt = 0
        var lastError: Throwable? = null
        while (attempt < 3) {
            try {
                return Result.Success(apiCall())
            } catch (e: Exception) {
                lastError = e
                attempt++
                delay(1000)
            }
        }
        return Result.Failure(lastError ?: Exception("Unknown Error"))
    }

    suspend fun getCurrencies(): Result<Map<String, String>> {
        return safeApiCall {
            val url = "https://api.frankfurter.dev/v1/currencies"
            client.get(url).body()
        }
    }

    suspend fun getExchangeRates(from: String, to: String): Result<Map<String, Double>> {
        return safeApiCall {
            val url = "https://api.frankfurter.dev/v1/latest?base=$from&symbols=$to"
            val response: ExchangeResponse = client.get(url).body()
            mapOf(to to (response.rates[to] ?: 0.0))
        }
    }

    suspend fun getExchangeGraph(from: String, to: String): Result<Map<String, Double>> {
        return safeApiCall {
            val url = "https://api.frankfurter.dev/v1/2024-01-01..?base=$from&symbols=$to"
            val response: ExchangeResponseGraph = client.get(url).body()
            response.rates.mapValues { it.value[to] ?: 0.0 }
        }
    }
}


@Serializable
data class ExchangeResponse(
    val rates: Map<String, Double>
)

@Serializable
data class ExchangeResponseGraph(
    val base: String,
    val start_date: String,
    val end_date: String,
    val rates: Map<String, Map<String, Double>>
)
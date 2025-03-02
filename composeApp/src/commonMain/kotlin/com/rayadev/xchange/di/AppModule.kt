package com.xchange.di

import com.rayadev.xchange.NumberFormatter
import com.rayadev.xchange.data.remote.HttpClientFactory
import com.xchange.data.remote.FrankfurterApi
import org.koin.dsl.module
import com.rayadev.xchange.di.Result

val appModule = module {
    single { get<HttpClientFactory>().createHttpClient() }
    single { FrankfurterApi(get()) }
    single { ExchangeService(get()) }
}


class ExchangeService(private val frankfurterApi: FrankfurterApi) {

    suspend fun convert(amount: Double, from: String, to: String): Result<Double> {
        return try {
            when (val exchangeRateResult = frankfurterApi.getExchangeRates(from, to)) {
                is Result.Success -> {
                    val exchangeRateMap = exchangeRateResult.value
                    val exchangeRate = exchangeRateMap[to] ?: return Result.Failure(Exception("Moneda destino no encontrada"))

                    Result.Success(amount * exchangeRate)
                }
                is Result.Failure -> {
                    Result.Failure(exchangeRateResult.exception)
                }
            }
        } catch (e: Exception) {
            Result.Failure(Exception("Error en la conversión: ${e.message}"))
        }
    }




    suspend fun getCurrencies(): Result<Map<String, String>> {
        return frankfurterApi.getCurrencies()
    }

    suspend fun getExchangeRates(from: String, to: String): Result<Map<String, Double>> {
        return frankfurterApi.getExchangeGraph(from, to)
    }
}




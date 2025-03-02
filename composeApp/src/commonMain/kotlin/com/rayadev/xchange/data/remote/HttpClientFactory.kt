package com.rayadev.xchange.data.remote

import io.ktor.client.*

expect class HttpClientFactory {
    fun createHttpClient(): HttpClient
}

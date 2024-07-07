package ru.somarov.berte.infrastructure.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun getKtor(): HttpClientEngine {
    return OkHttp.create {
        config {
            followRedirects(true)
            followSslRedirects(true)
        }
    }
}
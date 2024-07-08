package ru.somarov.berte.infrastructure.network

import io.ktor.client.engine.okhttp.OkHttp

actual fun getEngine() = OkHttp.create {
    config {
        followRedirects(true)
        followSslRedirects(true)
    }
}
package ru.somarov.berte.infrastructure.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

actual fun getKtor(): HttpClientEngine {
    return  Js.create {

    }
}
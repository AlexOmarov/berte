package ru.somarov.berte.infrastructure.network

import io.ktor.client.engine.js.Js

actual fun getEngine() = Js.create()

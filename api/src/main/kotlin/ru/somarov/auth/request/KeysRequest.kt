package ru.somarov.auth.request

import java.io.Serializable

data class KeysRequest(val alias: String, val encoding: String): Serializable

package ru.somarov.berte.layers.dto

data class BertePublicKey(
    val kty: String,
    val use: String,
    val kid: String,
    val n: String) {
}
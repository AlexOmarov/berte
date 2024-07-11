package ru.somarov.berte.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    val id: String,
    val username: String,
    val email: List<String>,
)

package ru.somarov.berte.conf.properties.dto.security

data class CorsProps(
    val origins: MutableList<String>,
    val methods: MutableList<String>?,
    val headers: MutableList<String>?,
    val allowCreds: Boolean?,
    val age: Long?,
    val exposedHeaders: MutableList<String>?
)

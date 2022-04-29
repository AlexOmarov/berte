package ru.somarov.berte.auth.config.properties.dto.security

data class SecurityProps(val cors: CorsProps, val open: MutableList<String>, val jwt: JwtProps)
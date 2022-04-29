package ru.somarov.berte.auth.properties.dto.security

data class SecurityProps(val cors: CorsProps, val open: MutableList<String>, val jwt: JwtProps)
package ru.somarov.berte.auth.service.auth.dto

import org.springframework.security.oauth2.jwt.Jwt

data class LoginResult(val access: Jwt?, val refresh: Jwt?, val remember: Jwt?)
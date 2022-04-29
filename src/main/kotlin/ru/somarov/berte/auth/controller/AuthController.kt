package ru.somarov.berte.auth.controller

import org.apache.commons.codec.binary.Base64
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.somarov.auth.request.KeysRequest
import ru.somarov.auth.request.LoginRequest
import ru.somarov.auth.response.KeysResponse
import ru.somarov.auth.response.LoginResponse
import ru.somarov.berte.auth.constants.AuthType
import ru.somarov.berte.auth.service.auth.AuthService
import ru.somarov.berte.auth.service.jwt.JwtService

@RestController("/auth")
class AuthController(private val authService: AuthService, private val jwtService: JwtService) {

    @PostMapping("/login")
    fun login(request: LoginRequest): Mono<LoginResponse> {
        return authService.login(request.login, request.password, request.codeChallenge, AuthType.valueOf(request.type.name)).map {
            LoginResponse(it)
        }
    }

    @PostMapping("/jwk")
    fun jwk(request: KeysRequest): Mono<KeysResponse> {
        return jwtService.public().map { KeysResponse(Base64.encodeBase64String(it.encoded)) }
    }
}
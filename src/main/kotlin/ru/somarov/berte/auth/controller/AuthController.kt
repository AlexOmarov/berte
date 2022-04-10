package ru.somarov.berte.auth.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.somarov.auth.request.LoginRequest
import ru.somarov.auth.response.LoginResponse
import ru.somarov.berte.auth.service.UserService

@RestController("/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("/login")
    fun login(request: LoginRequest): ResponseEntity<LoginResponse> {
        val result = userService.login(request.login, request.password, request.type)
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, result.access.tokenValue).body(LoginResponse(""))
    }
}
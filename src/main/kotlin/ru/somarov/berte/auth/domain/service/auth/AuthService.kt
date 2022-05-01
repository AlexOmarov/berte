package ru.somarov.berte.auth.domain.service.auth

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.auth.conf.constants.Provider
import ru.somarov.berte.auth.domain.service.jwt.JwtService
import ru.somarov.berte.auth.persistence.PersistenceFacade

@Service
class AuthService(private val authManager: ReactiveAuthenticationManager, private val jwtService: JwtService, persistence: PersistenceFacade) {

    fun login(username: String, password: String, codeChallenge: String, type: Provider): Mono<String> {
        return authManager.authenticate(createAuthentication(username, password, type)).map {
            jwtService.jwt(it.name)
        }
    }

    fun getTokens(code: String, clientId: String, clientSecret: String, codeValidator: String) {
        // For next
    }

    private fun createAuthentication(username: String, password: String, type: Provider): Authentication {
        return when(type) {
            Provider.HUAWEI -> UsernamePasswordAuthenticationToken(username, password)
        }
    }

}
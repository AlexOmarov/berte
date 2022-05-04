package ru.somarov.berte.business.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.business.dto.AuthorizationResult
import ru.somarov.berte.business.dto.BertePublicKey
import ru.somarov.berte.domain.service.auth.AuthService
import ru.somarov.berte.domain.service.jwt.JwtService
import ru.somarov.berte_api.constant.Provider

@Service
class AuthBusinessService(private val authService: AuthService, private val jwtService: JwtService) {

    fun login(login: String?, password: String?, codeChallenge: String, provider: Provider, type: String): Mono<String> {
        return authService.login(login as String, password as String, codeChallenge, provider, type).map {
            it.code
        }
    }

    fun getPublicJwk(alias: String, encoding: String): Mono<BertePublicKey> {
        TODO("Not yet implemented")
    }

    fun token(code: String, clientId: String, codeVerifier: String): Mono<AuthorizationResult> {
        return Mono.just(AuthorizationResult("","","",""))
    }

    fun revoke(revocations: String?, refresh: String?, rememberMe: String?, id: String?): Mono<Void> {
        return Mono.empty()
    }

    fun logout(id: String?, access: String, refresh: String, rememberMe: String?): Mono<Void> {
        return Mono.empty()
    }
}
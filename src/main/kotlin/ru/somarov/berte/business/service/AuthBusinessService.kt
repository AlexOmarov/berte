package ru.somarov.berte.business.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.business.dto.AuthorizationResult
import ru.somarov.berte.business.dto.BertePublicKey
import ru.somarov.berte.domain.service.TokenService
import ru.somarov.berte.domain.service.auth.AuthService
import ru.somarov.berte_api.constant.Provider

@Service
class AuthBusinessService(private val authService: AuthService, private val tokenService: TokenService) {

    fun authorize(
        login: String,
        password: String,
        codeChallenge: String,
        provider: Provider,
        clientId: String,
        oidc: Boolean
    ): Mono<String> {
        return authService.login(login, password, codeChallenge, provider, clientId, oidc).map { it.code }
    }

    fun getPublicJwk(alias: String, encoding: String): Mono<BertePublicKey> {
        return tokenService.getKey(alias, encoding)
    }

    fun token(code: String, clientId: String, verifier: String): Mono<AuthorizationResult> {
        return authService.getTokens(code, clientId, verifier)
    }

    fun revoke(revocations: String?, refresh: String?, rememberMe: String?, id: String?): Mono<Void> {
        return Mono.empty()
    }

    fun logout(id: String?, access: String, refresh: String, rememberMe: String?): Mono<Void> {
        return Mono.empty()
    }
}
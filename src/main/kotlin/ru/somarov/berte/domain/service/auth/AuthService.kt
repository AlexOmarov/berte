package ru.somarov.berte.domain.service.auth

import org.springframework.cache.CacheManager
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.business.dto.AuthorizationResult
import ru.somarov.berte.domain.dto.AuthorizationCodeInfo
import ru.somarov.berte.domain.service.TokenService
import ru.somarov.berte.domain.service.jwt.JwtService
import ru.somarov.berte.persistence.PersistenceFacade
import ru.somarov.berte_api.constant.Provider
import java.util.*

@Service
class AuthService(
    private val authManager: ReactiveAuthenticationManager,
    private val tokenService: TokenService,
    private val cacheManager: CacheManager,
    private val persistence: PersistenceFacade
) {

    private val cache = "code"

    fun login(username: String, password: String, codeChallenge: String, provider: Provider, clientId: String): Mono<AuthorizationCodeInfo> {
        return authManager.authenticate(createAuthentication(username, password, provider))
            .map {
                val info = AuthorizationCodeInfo(username, UUID.randomUUID().toString(), clientId, codeChallenge)
                cacheManager.getCache(cache)?.put(info.code, info)
                info
            }
    }

    fun getTokens(code: String, clientId: String, verifier: String): Mono<AuthorizationResult> {
        cacheManager.getCache(cache)?.get(code, AuthorizationCodeInfo::class.java)?.let {
            if(validate(it.codeChallenge, verifier, clientId, it.clientId)) {
                val result = tokenService.generateTokens(it.username)
            } else {

            }
        }
    }

    private fun validate(codeChallenge: String, verifier: String, incomingClientId: String, storedClientId: String): Boolean {
        TODO("Not yet implemented")
    }

    private fun createAuthentication(username: String, password: String, type: Provider): Authentication {
        return when(type) {
            Provider.NATIVE -> UsernamePasswordAuthenticationToken(username, password)
        }
    }

}
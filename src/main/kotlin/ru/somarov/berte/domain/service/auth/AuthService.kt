package ru.somarov.berte.domain.service.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.dto.AuthorizationCodeInfo
import ru.somarov.berte.domain.service.jwt.JwtService
import ru.somarov.berte.persistence.PersistenceFacade
import ru.somarov.berte_api.constant.Provider
import java.time.Duration
import java.util.*

@Service
class AuthService(
    private val authManager: ReactiveAuthenticationManager,
    private val jwtService: JwtService,
    private val cacheManager: CacheManager,
    persistence: PersistenceFacade
) {

    @Value("\${berte.cache.firstlevel}")
    private lateinit var firstLevelCacheDuration: Duration

    @Cacheable("code")
    fun login(username: String, password: String, codeChallenge: String, provider: Provider, type: String): Mono<AuthorizationCodeInfo> {
        return authManager.authenticate(createAuthentication(username, password, provider)).map {
            jwtService.jwt(it.name)
        }.map {
            AuthorizationCodeInfo(UUID.randomUUID(), it, "", "")
        }.cache(firstLevelCacheDuration)
    }

    fun getTokens(code: String, clientId: String, clientSecret: String, codeValidator: String) {
        // For next
    }

    private fun createAuthentication(username: String, password: String, type: Provider): Authentication {
        return when(type) {
            Provider.HUAWEI -> UsernamePasswordAuthenticationToken(username, password)
            Provider.NATIVE -> TODO()
        }
    }

}
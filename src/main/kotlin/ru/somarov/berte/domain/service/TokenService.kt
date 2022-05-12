package ru.somarov.berte.domain.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.conf.properties.AppProps
import ru.somarov.berte.domain.dto.AuthorizedTokenHolder
import ru.somarov.berte.persistence.PersistenceFacade
import java.security.PublicKey
import java.util.*

@Service
class TokenService(
    private val jwtService: JwtService,
    private val props: AppProps,
    private val persistence: PersistenceFacade
) {
    fun generateTokens(username: String, userId: UUID, oidc: Boolean): Mono<AuthorizedTokenHolder> {
        val access = jwtService.jwt(username, props.security.jwt.accessExpiration, false)
        val refresh = UUID.randomUUID().toString()
        persistence.saveRefresh(refresh, userId)
        val id = if (oidc) jwtService.jwt(username, props.security.jwt.accessExpiration, true) else null

        return Mono.just(AuthorizedTokenHolder(access, refresh, null, id))
    }

    fun getKey(alias: String, encoding: String): Mono<PublicKey> {
        return jwtService.public()
    }
}
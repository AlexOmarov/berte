package ru.somarov.berte.layers.domain

import org.springframework.security.core.token.Token
import org.springframework.security.core.token.TokenService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.conf.properties.BerteCustomProps
import ru.somarov.berte.layers.dto.AuthorizedTokenHolder
import ru.somarov.berte.layers.persistence.PersistenceFacade
import java.security.PublicKey
import java.util.*

@Service
class BerteTokenService (private val jwtService: JwtService, private val props: BerteCustomProps, private val persistence: PersistenceFacade): TokenService {

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

    override fun allocateToken(extendedInformation: String?): Token {
        TODO("Not yet implemented")
    }

    override fun verifyToken(key: String?): Token {
        TODO("Not yet implemented")
    }
}
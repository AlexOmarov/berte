package ru.somarov.berte.layers.domain

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.Payload
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jwt.JWTClaimsSet
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.conf.properties.BerteCustomProps
import java.security.PublicKey
import java.time.Duration
import java.util.*

@Service
class JwtService(private val props: BerteCustomProps) {

    fun jwt(name: String, duration: Duration, oidc: Boolean): String {
        val claims = JWTClaimsSet.Builder()
            .issueTime(Date())
            .expirationTime(Date(Date().time + duration.toMillis()))
            .subject(name)
            .claim("scope", "PLAYER")

        if (oidc) claims.claim("id", "sfg")

        val payload = Payload(claims.build().toJSONObject())
        val header = JWSHeader(JWSAlgorithm.RS512)
        val token = JWSObject(header, payload)
        token.sign(RSASSASigner(props.security.jwt.keys.private))
        return token.serialize(false)
    }

    fun public(): Mono<PublicKey> {
        return Mono.just(props.security.jwt.keys.public)
    }
}
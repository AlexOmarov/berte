package ru.somarov.berte.auth.service.jwt

import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jwt.JWTClaimsSet
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.auth.properties.AppProps
import java.security.PublicKey
import java.util.*


@Service
class JwtService(private val props: AppProps) {

    fun jwt(name: String): String {
        val claims = JWTClaimsSet.Builder()
            .issueTime(Date())
            .expirationTime(Date(Date().time + props.security.jwt.accessExpiration.toMillis()))
            .subject(name)
            .claim("scope", "PLAYER")
            .build()
        val payload = Payload(claims.toJSONObject())
        val header = JWSHeader(JWSAlgorithm.RS512)
        val token = JWSObject(header, payload)
        token.sign(RSASSASigner(props.security.jwt.keys.private))
        return token.serialize(false)
    }

    fun public(): Mono<PublicKey> {
        return Mono.just(props.security.jwt.keys.public)
    }
}
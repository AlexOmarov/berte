package ru.somarov.berte.auth.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.auth.domain.entity.Role
import ru.somarov.berte.auth.domain.entity.User
import java.util.*

@Service
class DefaultUserDetailsService : ReactiveUserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Value("\${app.user}")
    private lateinit var user: String

    override fun findByUsername(username: String): Mono<UserDetails> {
        val auths = mutableListOf(Role("ROLE_USER", OidcIdToken.withTokenValue("efef").build(), OidcUserInfo.builder().build()))
        return Mono.just(User(UUID.randomUUID(), username, passwordEncoder.encode("password"), auths))
    }
}
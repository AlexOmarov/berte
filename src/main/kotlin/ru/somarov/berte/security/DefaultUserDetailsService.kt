package ru.somarov.berte.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class DefaultUserDetailsService : ReactiveUserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Value("\${app.user}")
    private lateinit var user: String

    override fun findByUsername(username: String): Mono<UserDetails> {
        val auths = mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_USER"))
        return Mono.just(DefaultUserDetails(UUID.randomUUID(), username, auths, passwordEncoder.encode("password")))
    }
}
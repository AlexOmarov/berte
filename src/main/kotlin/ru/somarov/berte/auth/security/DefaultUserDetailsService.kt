package ru.somarov.berte.auth.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.stereotype.Service
import ru.somarov.berte.auth.domain.entity.Role
import ru.somarov.berte.auth.domain.entity.User
import java.util.*

@Service
class DefaultUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Value("\${berte.user}")
    private lateinit var user: String

    override fun loadUserByUsername(username: String?): UserDetails {
        val auths = mutableListOf(Role("ROLE_USER", OidcIdToken.withTokenValue("efef").build(), OidcUserInfo.builder().build()))
        return User(UUID.randomUUID(), username ?: "", passwordEncoder.encode("password"), auths)
    }
}
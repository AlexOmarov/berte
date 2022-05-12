package ru.somarov.berte.core.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.somarov.berte.persistence.PersistenceFacade

@Service
class DefaultUserDetailsService(persistence: PersistenceFacade) : UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        val auths = mutableListOf(SimpleGrantedAuthority( "PLAYER"))
        return User(username ?: "def", passwordEncoder.encode("password"), auths)
    }
}
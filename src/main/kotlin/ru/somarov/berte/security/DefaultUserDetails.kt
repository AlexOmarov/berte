package ru.somarov.berte.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class DefaultUserDetails(
    private val id: UUID,
    private val username: String,
    private val auths: MutableCollection<out GrantedAuthority>,
    private val password: String
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return auths
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
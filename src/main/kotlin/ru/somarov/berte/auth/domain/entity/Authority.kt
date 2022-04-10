package ru.somarov.berte.auth.domain.entity

import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority

@Table("security_authority")
class Authority(private val code: String) : GrantedAuthority {
    override fun getAuthority(): String {
        return code
    }
}
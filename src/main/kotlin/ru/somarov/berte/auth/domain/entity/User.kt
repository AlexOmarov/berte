package ru.somarov.berte.auth.domain.entity

import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.userdetails.User
import java.util.*

@Table("security_user")
class User(private val id: UUID, username: String, password: String, authorities: MutableCollection<out Role>) : User(username, password, authorities)
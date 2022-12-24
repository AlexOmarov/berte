package ru.somarov.berte.layers.persistence.entity

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("security_refresh_token")
data class RefreshTokenEntity(
    var id: UUID?,
    var userId: UUID,
)

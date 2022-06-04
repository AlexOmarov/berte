package ru.somarov.berte.layers.persistence.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.layers.persistence.entity.RefreshTokenEntity
import java.util.*

interface RefreshTokenRepo : ReactiveCrudRepository<RefreshTokenEntity, UUID>

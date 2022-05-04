package ru.somarov.berte.persistence.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.persistence.entity.InvalidTokenEntity

interface InvalidTokenRepo: ReactiveCrudRepository<InvalidTokenEntity, Short>
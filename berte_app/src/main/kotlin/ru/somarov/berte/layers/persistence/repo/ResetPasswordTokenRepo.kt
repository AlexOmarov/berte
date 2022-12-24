package ru.somarov.berte.layers.persistence.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.layers.persistence.entity.ResetPasswordTokenEntity

interface ResetPasswordTokenRepo: ReactiveCrudRepository<ResetPasswordTokenEntity, Short>
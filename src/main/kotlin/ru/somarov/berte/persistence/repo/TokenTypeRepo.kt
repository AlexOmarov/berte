package ru.somarov.berte.persistence.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.persistence.entity.TokenTypeEntity

interface TokenTypeRepo: ReactiveCrudRepository<TokenTypeEntity, Short>
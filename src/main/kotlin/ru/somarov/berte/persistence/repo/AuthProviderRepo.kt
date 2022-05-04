package ru.somarov.berte.persistence.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.persistence.entity.AuthProviderEntity

interface AuthProviderRepo: ReactiveCrudRepository<AuthProviderEntity, Short>
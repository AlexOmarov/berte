package ru.somarov.berte.auth.persistence.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.auth.persistence.entity.RoleEntity

interface RoleRepo: ReactiveCrudRepository<RoleEntity, Short>
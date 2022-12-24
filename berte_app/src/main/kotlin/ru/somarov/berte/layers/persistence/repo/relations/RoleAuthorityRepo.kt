package ru.somarov.berte.layers.persistence.repo.relations

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.layers.persistence.entity.relations.RoleAuthorityEntity

interface RoleAuthorityRepo: ReactiveCrudRepository<RoleAuthorityEntity, Short>
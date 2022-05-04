package ru.somarov.berte.persistence.repo.relations

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.persistence.entity.relations.RoleAuthorityEntity

interface RoleAuthorityRepo: ReactiveCrudRepository<RoleAuthorityEntity, Short>
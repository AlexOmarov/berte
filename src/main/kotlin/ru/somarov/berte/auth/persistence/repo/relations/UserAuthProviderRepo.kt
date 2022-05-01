package ru.somarov.berte.auth.persistence.repo.relations

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.auth.persistence.entity.relations.UserAuthProviderEntity

interface UserAuthProviderRepo: ReactiveCrudRepository<UserAuthProviderEntity, Short>
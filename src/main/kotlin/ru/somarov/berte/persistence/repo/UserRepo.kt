package ru.somarov.berte.persistence.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.somarov.berte.persistence.entity.UserEntity
import java.util.*

interface UserRepo: ReactiveCrudRepository<UserEntity, UUID>
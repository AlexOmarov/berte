package ru.somarov.berte.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.entity.User
import ru.somarov.berte.domain.repo.Dao
import java.util.*

/**
 * Service for handling interactions with user
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author alexandr.omarov
 *
 */

@Service
class UserService(private val dao: Dao) {

    fun register(email: String): Mono<User> {
        val user = User(UUID.randomUUID(), email)
        dao.saveUser(user)
        return Mono.just(user)
    }

    fun get(email: String): Mono<User> {
        return dao.getUser(email)
    }

    fun get(id:UUID): Mono<User> {
        return dao.getUser(id)
    }
}
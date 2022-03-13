package ru.somarov.berte.domain.repo

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.entity.User
import java.util.*


@Repository
interface UserRepo : ReactiveCrudRepository<User, UUID> {
    fun findByEmail(email:String): Mono<User>

    @Modifying
    @Query(value = "INSERT into User (id, email) VALUES (:id, :email)")
    @Transactional
    fun insert(@Param("id") id: UUID, @Param("email") email: String)

}
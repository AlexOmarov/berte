package ru.somarov.berte.layers.persistence.dao

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import ru.somarov.berte.layers.persistence.entity.RefreshTokenEntity
import ru.somarov.berte.layers.persistence.repo.InvalidTokenRepo
import ru.somarov.berte.layers.persistence.repo.RefreshTokenRepo
import ru.somarov.berte.layers.persistence.repo.ResetPasswordTokenRepo
import ru.somarov.berte.layers.persistence.repo.TokenTypeRepo
import java.util.*

@Component
class TokenDao(
    private val invalidTokenRepo: InvalidTokenRepo,
    private val resetPasswordTokenRepo: ResetPasswordTokenRepo,
    private val tokenTypeRepo: TokenTypeRepo,
    private val refreshTokenRepo: RefreshTokenRepo,
    private val template: R2dbcEntityTemplate,
    private val client: DatabaseClient,
    private val transactionalOperator: TransactionalOperator
) {
    fun refresh(refresh: UUID, userId: UUID): Mono<RefreshTokenEntity> {
        return refreshTokenRepo.save(RefreshTokenEntity(refresh, userId))
    }
}

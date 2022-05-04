package ru.somarov.berte.persistence.dao

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import ru.somarov.berte.persistence.repo.InvalidTokenRepo
import ru.somarov.berte.persistence.repo.ResetPasswordTokenRepo
import ru.somarov.berte.persistence.repo.TokenTypeRepo

@Component
class TokenDao(
    private val invalidTokenRepo: InvalidTokenRepo,
    private val resetPasswordTokenRepo: ResetPasswordTokenRepo,
    private val tokenTypeRepo: TokenTypeRepo,
    private val template: R2dbcEntityTemplate,
    private val client: DatabaseClient,
    private val transactionalOperator: TransactionalOperator
) {
}
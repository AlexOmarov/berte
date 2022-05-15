package ru.somarov.berte.layers.persistence.dao

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import ru.somarov.berte.layers.persistence.repo.AuthProviderRepo
import ru.somarov.berte.layers.persistence.repo.AuthorityRepo
import ru.somarov.berte.layers.persistence.repo.RoleRepo
import ru.somarov.berte.layers.persistence.repo.UserRepo
import ru.somarov.berte.layers.persistence.repo.relations.RoleAuthorityRepo
import ru.somarov.berte.layers.persistence.repo.relations.UserAuthProviderRepo
import ru.somarov.berte.layers.persistence.repo.relations.UserRoleRepo

@Component
class UserDao(
    private val userRepo: UserRepo,
    private val roleRepo: RoleRepo,
    private val authorityRepo: AuthorityRepo,
    private val userRoleRepo: UserRoleRepo,
    private val userAuthProviderRepo: UserAuthProviderRepo,
    private val authProviderRepo: AuthProviderRepo,
    private val roleAuthorityRepo: RoleAuthorityRepo,
    private val template: R2dbcEntityTemplate,
    private val client: DatabaseClient,
    private val transactionalOperator: TransactionalOperator
) {
}
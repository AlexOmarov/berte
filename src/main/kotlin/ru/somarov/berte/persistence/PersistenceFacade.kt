package ru.somarov.berte.persistence

import org.springframework.stereotype.Component
import ru.somarov.berte.converter.PersistenceDomainConverter
import ru.somarov.berte.persistence.dao.TokenDao
import ru.somarov.berte.persistence.dao.UserDao

@Component
class PersistenceFacade (
    private val invalidTokenRepo: TokenDao,
    private val resetPasswordTokenRepo: UserDao,
    private val converter: PersistenceDomainConverter,
) {

}
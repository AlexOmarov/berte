package ru.somarov.berte.persistence.entity.relations

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.somarov.berte.persistence.entity.AuthorityEntity
import ru.somarov.berte.persistence.entity.RoleEntity
import java.util.*

@Table("security_role_security_authority")
class RoleAuthorityEntity(
    var id: UUID?,
    @Column("id_security_authority")
    var authorityId: Short,
    @Column("id_security_role")
    var roleId: Short
) {
    var authority: AuthorityEntity? = null
    var role: RoleEntity? = null
}
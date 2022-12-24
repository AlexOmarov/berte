package ru.somarov.berte.layers.persistence.entity.relations

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.somarov.berte.layers.persistence.entity.RoleEntity
import ru.somarov.berte.layers.persistence.entity.UserEntity
import java.util.*

@Table("security_user_security_role")
class UserRoleEntity(
    var id: UUID?,
    @Column("id_security_authority")
    var userId: UUID,
    @Column("id_security_role")
    var roleId: Short
) {
    var user: UserEntity? = null
    var role: RoleEntity? = null
}
package ru.somarov.berte.layers.persistence.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Table("security_user")
class UserEntity(
    @Id
    var id: UUID?,

    @Size(max=512)
    @NotBlank
    var login: String,

    @Size(max=512)
    @NotBlank
    var password: String,

    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var enabled: Boolean,
    var ivVector: String,
) {

    @CreatedDate
    var createdAt: OffsetDateTime? = null

    @LastModifiedDate
    var modifiedAt: OffsetDateTime?  = null
}
package ru.somarov.berte.layers.persistence.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Table("security_invalid_token")
class InvalidTokenEntity(
    var id: UUID?,
    @Size(max=512)
    @NotBlank
    var code: String,
    @Column("id_security_token_type")
    var type: Short,
    @Column("id_security_user")
    var user: UUID,
    var expirationDate: OffsetDateTime
) {

}
package ru.somarov.berte.auth.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Table("security_reset_password_token")
class ResetPasswordTokenEntity(
    @Id
    var id: UUID?,

    @Size(max=512)
    @NotBlank
    var value: String,

    var expirationDate: OffsetDateTime,

    @Column("id_security_user")
    var user: UUID
) {

}
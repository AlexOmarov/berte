package ru.somarov.berte.auth.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Table("security_user")
class User(
    @Id
    var id: UUID?,

    @Size(max=512)
    @NotBlank
    var username: String,

    @Size(max=512)
    @NotBlank
    var password: String,

    @Version
    var version: Short
) {

    @Transient
    var authorities: MutableCollection<out Role> = mutableListOf()

    @CreatedDate
    var createdAt: OffsetDateTime? = null

    @LastModifiedDate
    var modifiedAt: OffsetDateTime?  = null


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (authorities != other.authorities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + authorities.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, username='$username', password='$password', authorities=$authorities)"
    }
}
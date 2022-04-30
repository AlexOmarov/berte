package ru.somarov.berte.auth.domain.entity

import org.springframework.data.relational.core.mapping.Table

@Table("security_authority")
class Authority(
    var id: Short?,
    var code: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Authority

        if (id != other.id) return false
        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        var result: Int = (id ?: 0).toInt()
        result = 31 * result + code.hashCode()
        return result
    }

    override fun toString(): String {
        return "Authority(id=$id, code=$code)"
    }
}
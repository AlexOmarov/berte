package ru.somarov.berte.infrastructure.uuid

import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom

class UUID private constructor() {
    private var uuid = uuid4()

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is UUID && this.uuid == other.uuid
    }

    override fun toString(): String {
        return uuid.toString()
    }

    private fun parse(string: String) {
        uuid = uuidFrom(string)
    }

    companion object {
        fun from(string: String): UUID {
            return UUID().also { it.parse(string) }
        }

        fun generate(): UUID {
            return UUID()
        }
    }
}

package ru.somarov.berte.infrastructure.uuid

import com.benasher44.uuid.uuid4

fun createUniqueString(): String {
    return uuid4().toString()
}

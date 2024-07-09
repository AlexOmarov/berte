package ru.somarov.berte.infrastructure

import com.benasher44.uuid.uuid4

fun createUUID(): String {
    return uuid4().toString()
}

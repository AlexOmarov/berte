package ru.somarov.berte_api.message

import ru.somarov.berte_api.standard.BerteMessage
import java.io.Serializable
import java.util.*

data class SimpleMessage(val value: String, val id: UUID): BerteMessage(), Serializable

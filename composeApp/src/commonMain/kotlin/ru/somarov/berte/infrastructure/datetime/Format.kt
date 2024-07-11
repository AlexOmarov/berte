package ru.somarov.berte.infrastructure.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

object Format {
    val format = LocalDateTime.Format {
        dayOfMonth(padding = Padding.ZERO)
        char('.')
        monthNumber(padding = Padding.ZERO)
        char('.')
        year(padding = Padding.ZERO)
        char(' ')
        hour(padding = Padding.ZERO)
        char(':')
        minute(padding = Padding.ZERO)
        char(':')
        second(padding = Padding.ZERO)
    }
}

package ru.somarov.berte

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BerteApplication

fun main(args: Array<String>) {
	runApplication<BerteApplication>(*args)
}

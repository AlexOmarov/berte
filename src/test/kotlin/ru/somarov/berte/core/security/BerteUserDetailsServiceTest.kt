package ru.somarov.berte.core.security

import io.mockk.mockk
import org.junit.jupiter.api.Test

class BerteUserDetailsServiceTest {

    private val service: BerteUserDetailsService = BerteUserDetailsService(mockk())

    @Test
    fun `Service loads user details from persistence layer by name`() {
        // TODO: test service
    }
}
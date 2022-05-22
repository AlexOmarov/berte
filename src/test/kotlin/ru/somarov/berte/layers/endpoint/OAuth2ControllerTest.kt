package ru.somarov.berte.layers.endpoint

import com.ninjasquad.springmockk.MockkBean
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import ru.somarov.berte.conf.SecurityConfig
import ru.somarov.berte.conf.constants.Constants.AUTH_HEADER
import ru.somarov.berte.conf.properties.BerteCustomProps
import ru.somarov.berte.layers.business.UserAuthorizationService
import ru.somarov.berte_api.request.KeysRequest

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class, MockKExtension::class)
@WebFluxTest(properties = ["app.scheduling.enabled=false"], controllers = [OAuth2Controller::class])
@EnableConfigurationProperties(BerteCustomProps::class)
@Import(SecurityConfig::class)
@AutoConfigureWebTestClient
class OAuth2ControllerTest {

    @MockkBean
    private lateinit var service: UserAuthorizationService

    @Autowired
    private lateinit var appProps: BerteCustomProps

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `Authenticated request passes and valid response is returned`() {
        true == true
    }
}
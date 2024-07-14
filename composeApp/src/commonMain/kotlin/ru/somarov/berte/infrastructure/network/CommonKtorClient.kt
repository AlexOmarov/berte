package ru.somarov.berte.infrastructure.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.BadGateway
import io.ktor.http.HttpStatusCode.Companion.GatewayTimeout
import io.ktor.http.HttpStatusCode.Companion.RequestTimeout
import io.ktor.http.HttpStatusCode.Companion.ServiceUnavailable
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.serialization.json.Json

fun getDefaultClient(props: HttpClientProps): HttpClient {
    return HttpClient(getEngine()) {
        install(HttpCache)
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        install(HttpRedirect) { checkHttpMethod = true }
        install(HttpCookies) { props.storage?.let { storage = props.storage } }

        install(ContentEncoding) {
            deflate(props.deflateFactor)
            gzip(props.gzipFactor)
        }

        install(Auth) {
            bearer {
                loadTokens {
                    props.tokenStorage.last()
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = props.requestTimeoutMillis
            socketTimeoutMillis = props.socketTimeoutMillis
            connectTimeoutMillis = props.connectTimeoutMillis
        }

        install(HttpRequestRetry) {
            maxRetries = props.maxRetries
            retryIf { _, httpResponse ->
                httpResponse.status in listOf(
                    RequestTimeout,
                    GatewayTimeout,
                    ServiceUnavailable,
                    BadGateway
                )
            }
        }
    }
}

data class HttpClientProps(
    val deflateFactor: Float = 1.0F,
    val gzipFactor: Float = 0.9F,
    val tokenStorage: Flow<BearerTokens>,
    val requestTimeoutMillis: Long = 5000L,
    val socketTimeoutMillis: Long = 5000L,
    val connectTimeoutMillis: Long = 5000L,
    val maxRetries: Int = 3,
    val storage: CookiesStorage? = null,
    val codesToRetry: List<HttpStatusCode> = listOf(
        RequestTimeout,
        GatewayTimeout,
        ServiceUnavailable,
        BadGateway
    ),
)

package ru.somarov.berte.infrastructure.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpStatusCode.Companion.BadGateway
import io.ktor.http.HttpStatusCode.Companion.GatewayTimeout
import io.ktor.http.HttpStatusCode.Companion.RequestTimeout
import io.ktor.http.HttpStatusCode.Companion.ServiceUnavailable
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object Engine {
    fun HttpClientConfig<*>.installEncoding() {
        install(ContentEncoding) {
            deflate(1.0F)
            gzip(0.9F)
        }
    }

    fun HttpClientConfig<*>.installCache() {
        install(HttpCache)
    }

    fun HttpClientConfig<*>.installLogger(logLevel: LogLevel) {
        install(Logging) {
            level = logLevel
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun HttpClientConfig<*>.installJson() {
        installEncoding()
        install(ContentNegotiation) {
            this.json(Json {
                prettyPrint = false
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }
    fun HttpClientConfig<*>.installBasic(username: String, password: String) {
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = username, password = password)
                }
                sendWithoutRequest { true }
            }
        }
    }
    fun HttpClientConfig<*>.installBearer(accessToken: String, refreshToken: String? = null) {
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken, refreshToken)
                }
                sendWithoutRequest { true }
            }
        }
    }

    fun HttpClientConfig<*>.installDefault(storage: CookiesStorage?) {
        install(HttpTimeout) {
            this.requestTimeoutMillis = requestTimeoutMillis
            this.socketTimeoutMillis = socketTimeoutMillis
            this.connectTimeoutMillis = connectTimeoutMillis
        }
        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, httpResponse ->
                httpResponse.status in retryList
            }
        }

        install(HttpRedirect) {
            checkHttpMethod = true
        }
        if (storage != null) {
            install(HttpCookies) {
                this.storage = storage
            }
        }
        followRedirects = true
        developmentMode = false
    }

    fun clientCommon(
        storage: CookiesStorage? = null,
        init: HttpClientConfig<*>.() -> Unit = {}
    ): HttpClient {
        return HttpClient(getKtor()) {
            installDefault(storage)
            apply(init)
        }
    }

    private val retryList = listOf(
        RequestTimeout,
        GatewayTimeout,
        ServiceUnavailable,
        BadGateway
    )
}
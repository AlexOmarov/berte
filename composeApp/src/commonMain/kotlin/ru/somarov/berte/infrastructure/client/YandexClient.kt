package ru.somarov.berte.infrastructure.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.somarov.berte.application.dto.auth.Token

class YandexClient(private val client: HttpClient) {

    suspend fun getUserInfo(token: Token): YandexUser {
        val response = client.get("$HOST/$USER_INFO_PATH") {
            header("Authorization", "OAuth ${token.value}")
        }
        if (response.status != HttpStatusCode.OK)
            throw YandexNetworkException(
                response.status.value,
                runCatching { response.bodyAsText() }.getOrNull() ?: "Error"
            )
        return response.body<YandexUser>()
    }

    companion object {
        const val HOST = "https://login.yandex.ru"
        const val USER_INFO_PATH = "info"
    }

    @Serializable
    data class YandexUser(
        @SerialName("client_id")
        val clientId: String,
        @SerialName("default_email")
        val defaultEmail: String,
        @SerialName("id")
        val id: String,
        @SerialName("is_avatar_empty")
        val isAvatarEmpty: Boolean,
        @SerialName("login")
        val login: String,
    )

    class YandexNetworkException(val code: Int, message: String) : Exception(message)
}

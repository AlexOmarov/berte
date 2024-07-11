package ru.somarov.berte.infrastructure.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.somarov.berte.application.dto.AuthUser
import ru.somarov.berte.infrastructure.oauth.TokenStore

object YandexApi {

    @OptIn(ExperimentalSerializationApi::class)
    private fun createHttpClient(): HttpClient {
        return HttpClient(getEngine()) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = false
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }
        }
    }

    suspend fun getUserInfo(token: TokenStore): YandexUser {
        val response = createHttpClient().get("https://login.yandex.ru/info") {
            header("Authorization", "OAuth ${token.value}")
        }
        if (response.status != HttpStatusCode.OK)
            throw YandexNetworkException(
                response.status.value,
                runCatching { response.bodyAsText() }.getOrNull() ?: "Error"
            )
        return response.body<YandexUser>()
    }
}

fun YandexUser.toAuthUser(): AuthUser {
    return AuthUser(
        id = "yandex-$id",
        username = this.displayName,
        email = this.emails,
    )
}

@Serializable
data class DefaultPhone(
    @SerialName("id")
    val id: Int,
    @SerialName("number")
    val number: String
)

@Serializable
data class YandexUser(
    @SerialName("birthday")
    val birthday: String,
    @SerialName("client_id")
    val clientId: String,
    @SerialName("default_avatar_id")
    val defaultAvatarId: String,
    @SerialName("default_email")
    val defaultEmail: String,
    @SerialName("default_phone")
    val defaultPhone: DefaultPhone,
    @SerialName("display_name")
    val displayName: String,
    @SerialName("emails")
    val emails: List<String>,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("id")
    val id: String,
    @SerialName("is_avatar_empty")
    val isAvatarEmpty: Boolean,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("login")
    val login: String,
    @SerialName("psuid")
    val psuid: String,
    @SerialName("real_name")
    val realName: String,
    @SerialName("sex")
    val sex: String
)

class YandexNetworkException(val code: Int, message: String) : Exception(message)

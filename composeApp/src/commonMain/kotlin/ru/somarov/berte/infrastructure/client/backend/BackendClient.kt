package ru.somarov.berte.infrastructure.client.backend

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import kotlinx.coroutines.flow.Flow
import ru.somarov.berte.application.dto.message.Message
import ru.somarov.berte.infrastructure.client.backend.request.CloseSessionRequest
import ru.somarov.berte.infrastructure.client.backend.request.CreateSessionRequest
import ru.somarov.berte.infrastructure.client.backend.request.GetSessionDetailsRequest
import ru.somarov.berte.infrastructure.client.backend.request.GetSessionsRequest
import ru.somarov.berte.infrastructure.client.backend.request.JoinSessionRequest
import ru.somarov.berte.infrastructure.client.backend.request.QuitSessionRequest
import ru.somarov.berte.infrastructure.client.backend.request.RegistrationRequest
import ru.somarov.berte.infrastructure.client.backend.request.SendMessageRequest
import ru.somarov.berte.infrastructure.client.backend.response.CloseSessionResponse
import ru.somarov.berte.infrastructure.client.backend.response.CreateSessionResponse
import ru.somarov.berte.infrastructure.client.backend.response.GetSessionDetailsResponse
import ru.somarov.berte.infrastructure.client.backend.response.GetSessionsResponse
import ru.somarov.berte.infrastructure.client.backend.response.JoinSessionResponse
import ru.somarov.berte.infrastructure.client.backend.response.QuitSessionResponse
import ru.somarov.berte.infrastructure.client.backend.response.RegistrationResponse
import ru.somarov.berte.infrastructure.client.backend.response.SendMessageResponse

class BackendClient(private val httpClient: HttpClient) {
    private val sessions = mutableMapOf<String, ClientWebSocketSession>()

    suspend fun register(request: RegistrationRequest): RegistrationResponse {
        TODO("implement")
    }

    suspend fun createSession(request: CreateSessionRequest): CreateSessionResponse {
        TODO("implement")
    }

    suspend fun joinSession(request: JoinSessionRequest): JoinSessionResponse {
        TODO("implement")
    }

    suspend fun getSessions(request: GetSessionsRequest): GetSessionsResponse {
        TODO("implement")
    }

    suspend fun getSessionDetails(request: GetSessionDetailsRequest): GetSessionDetailsResponse {
        TODO("implement")
    }

    fun connect(sessionId: String): Flow<Message> {
        TODO("implement")
    }

    suspend fun sendMessage(request: SendMessageRequest): SendMessageResponse {
        TODO("implement")
    }

    suspend fun quitSession(request: QuitSessionRequest): QuitSessionResponse {
        TODO("implement")
    }

    suspend fun closeSession(request: CloseSessionRequest): CloseSessionResponse {
        TODO("implement")
    }
}

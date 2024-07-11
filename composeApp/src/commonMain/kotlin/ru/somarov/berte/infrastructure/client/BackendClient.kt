package ru.somarov.berte.infrastructure.client

import com.benasher44.uuid.Uuid
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import kotlinx.coroutines.flow.Flow
import ru.somarov.berte.application.dto.Message
import ru.somarov.berte.infrastructure.client.request.CloseSessionRequest
import ru.somarov.berte.infrastructure.client.request.CreateSessionRequest
import ru.somarov.berte.infrastructure.client.request.GetSessionDetailsRequest
import ru.somarov.berte.infrastructure.client.request.GetSessionsRequest
import ru.somarov.berte.infrastructure.client.request.JoinSessionRequest
import ru.somarov.berte.infrastructure.client.request.QuitSessionRequest
import ru.somarov.berte.infrastructure.client.request.RegistrationRequest
import ru.somarov.berte.infrastructure.client.request.SendMessageRequest
import ru.somarov.berte.infrastructure.client.response.CloseSessionResponse
import ru.somarov.berte.infrastructure.client.response.CreateSessionResponse
import ru.somarov.berte.infrastructure.client.response.GetSessionDetailsResponse
import ru.somarov.berte.infrastructure.client.response.GetSessionsResponse
import ru.somarov.berte.infrastructure.client.response.JoinSessionResponse
import ru.somarov.berte.infrastructure.client.response.QuitSessionResponse
import ru.somarov.berte.infrastructure.client.response.RegistrationResponse
import ru.somarov.berte.infrastructure.client.response.SendMessageResponse

class BackendClient(private val httpClient: HttpClient) {
    private val sessions = mutableMapOf<Uuid, ClientWebSocketSession>()

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

    suspend fun connect(sessionId: Uuid): Flow<Message> {
        TODO("implement")
    }

    suspend fun sendMessage(request: SendMessageRequest): SendMessageResponse {
        TODO("implement")
    }

    suspend fun disconnect(sessionId: Uuid): Boolean {
        TODO("implement")
    }

    suspend fun quitSession(request: QuitSessionRequest): QuitSessionResponse {
        TODO("implement")
    }

    suspend fun closeSession(request: CloseSessionRequest): CloseSessionResponse {
        TODO("implement")
    }
}

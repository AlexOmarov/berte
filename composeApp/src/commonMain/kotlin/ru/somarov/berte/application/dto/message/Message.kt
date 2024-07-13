package ru.somarov.berte.application.dto.message

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val text: String,
    val messageType: MessageType,
    val date: Instant = Clock.System.now(),
    val spiritMessage: MessageSpirit = MessageSpirit.Neutral
) {
    enum class MessageSpirit(val info: MessageSpiritInfo) {
        Catastrophic(
            MessageSpiritInfo(
                background = "http://berte.ru/store/catastrophic.png",
                tint = "D0D0D0"
            )
        ),
        Critical(
            MessageSpiritInfo(
                background = "http://berte.ru/store/critical.png",
                tint = "D0A0A0"
            )
        ),
        Dangerous(
            MessageSpiritInfo(
                background = "http://berte.ru/store/dangerous.png",
                tint = "A0A0A0"
            )
        ),
        Neutral(
            MessageSpiritInfo(
                background = "http://berte.ru/store/neutral.png",
                tint = "505050"
            )
        ),
        Favorable(
            MessageSpiritInfo(
                background = "http://berte.ru/store/favorable.png",
                tint = "50A070"
            )
        ),
        Good(
            MessageSpiritInfo(
                background = "http://berte.ru/store/good.png",
                tint = "50D070"
            )
        ),
        Excellent(
            MessageSpiritInfo(
                background = "http://berte.ru/store/excellent.png",
                tint = "50D0D0"
            )
        );

        @Serializable
        data class MessageSpiritInfo(
            val background: String,
            val tint: String,
            val music: String? = null,
        )
    }

    enum class MessageType { REQUEST, RESPONSE }
}

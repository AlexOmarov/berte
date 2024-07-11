package ru.somarov.berte.application.dto

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.somarov.berte.application.dto.message.MessageSpirit
import ru.somarov.berte.application.dto.message.MessageType
import ru.somarov.berte.application.dto.message.MessageUIEvent

@Serializable
data class Message(
    val text: String,
    val messageType: MessageType,
    val date: Instant = Clock.System.now(),
    // в ui элементе сообщения показывается загруженная картинка
    val iconUrl: String? = null,

    // позволяет перейти из сообщения на отдельный экран
    val route: String? = null,

    // сразу после получения проигрывает звук или проигрывает анимацию
    // или производит какое либо иное действие с текущим UI, но не меняет
    // дух повествования
    val uiEvent: MessageUIEvent? = null,

    // определяет настрой истории в текущий момент меняет
    // визуальную составляющую всего экрана
    // backgorund image, tint цвета, меняет фоновую музыку
    val spiritStore: MessageSpirit = MessageSpirit.Neutral,

    // определяет настрой сообщения
    // меняет визуальную составляющую сообщения
    // backgorund image, tint цвета, при действии с сообщением
    // меняет фоновую музыку
    val spiritMessage: MessageSpirit = MessageSpirit.Neutral
)

package ru.somarov.berte.application.dto

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
enum class MessageType { InMessage, OutMessage, Warning }

@Serializable
data class Message(
    val text: String,
    val messageType: MessageType,
    val date: Instant = Clock.System.now(),

    // позиция в игровом мире
    val position: MapPosition?,

    // в ui элементе сообщения показывается загруженная картинка
    val iconUrl: String? = null,

    // позволяет перейти из сообщения на отдельный экран
    val route: String? = null,

    // сразу после получения проигрывает звук или проигрывает анимацию
    // или производит какое либо иное действие с текущим UI, но не меняет
    // дух повествования
    val uiEvent: MessageUiEvent? = null,

    // определяет настрой истории в текущий момент меняет
    // визуальную составляющую всего экрана
    // backgorund image, tint цвета, меняет фоновую музыку
    val spiritStore: MessageSpirit = MessageSpiritCase.Neutral.spirit,

    // определяет настрой сообщения
    // меняет визуальную составляющую сообщения
    // backgorund image, tint цвета, при действии с сообщением
    // меняет фоновую музыку
    val spiritMessage: MessageSpirit = MessageSpiritCase.Neutral.spirit
)

@Serializable
data class MessageUiEvent(
    val animation: MessageAnimation? = null,
    val sound: String?
)

// какие то случайные названия
// в последствии на фронте будут реализованы осмысленные
// названия которыми можно будет управлять с сервера
@Serializable
enum class MessageAnimation {
    Bam,
    Buh,
    Trah,
    Ura
}

@Serializable
data class MessageSpirit(
    val background: String,
    val tint: String,
    val music: String? = null,
)

// предустановленные состояния
@Serializable
enum class MessageSpiritCase(val spirit: MessageSpirit) {
    Catastrophic(
        MessageSpirit(
            background = "http://berte.ru/store/catastrophic.png",
            tint = "D0D0D0"
        )
    ),
    Critical(
        MessageSpirit(
            background = "http://berte.ru/store/critical.png",
            tint = "D0A0A0"
        )
    ),
    Dangerous(
        MessageSpirit(
            background = "http://berte.ru/store/dangerous.png",
            tint = "A0A0A0"
        )
    ),
    Neutral(
        MessageSpirit(
            background = "http://berte.ru/store/neutral.png",
            tint = "505050"
        )
    ),
    Favorable(
        MessageSpirit(
            background = "http://berte.ru/store/favorable.png",
            tint = "50A070"
        )
    ),
    Good(
        MessageSpirit(
            background = "http://berte.ru/store/good.png",
            tint = "50D070"
        )
    ),
    Excellent(
        MessageSpirit(
            background = "http://berte.ru/store/excellent.png",
            tint = "50D0D0"
        )
    ),
}

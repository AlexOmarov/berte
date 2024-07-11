package ru.somarov.berte.ui.element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import ru.somarov.berte.application.dto.Message
import ru.somarov.berte.application.dto.message.MessageType
import ru.somarov.berte.infrastructure.datetime.Format

@Composable
fun Message(message: Message, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val date = remember(message.date) {
        message.date.toLocalDateTime(TimeZone.currentSystemDefault())
            .format(Format.format)
    }
    Card(
        onClick = onClick,
        modifier = modifier.paddingMessage(message)
            .widthIn(min = 200.dp, max = 400.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = date,
                modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                textAlign = when (message.messageType) {
                    MessageType.IN -> TextAlign.Left
                    MessageType.OUT -> TextAlign.Right
                }
            )

            Text(
                text = message.text,
                modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = when (message.messageType) {
                    MessageType.IN -> TextAlign.Left
                    MessageType.OUT -> TextAlign.Right
                }
            )
        }
    }
}

private fun Modifier.paddingMessage(message: Message): Modifier {
    return when (message.messageType) {
        MessageType.IN -> padding(end = 28.dp)
        MessageType.OUT -> padding(start = 28.dp)
    }
}

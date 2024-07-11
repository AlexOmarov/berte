package ru.somarov.berte.ui.element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ru.somarov.berte.application.dto.auth.AuthUser

@Composable
fun UserDialog(user: AuthUser, onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.padding(horizontal = 20.dp),
        shape = MaterialTheme.shapes.small,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        onDismissRequest = onDismiss,
        text = {
            Column {
                Text(user.username)
                HorizontalDivider()
                user.email.forEach {
                    Text(it)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Закрыть")
            }
        })
}

package ru.somarov.berte.ui.element

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.somarov.berte.application.dto.auth.AuthUser
import ru.somarov.berte.ui.Screen

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    current: Screen,
    user: AuthUser?,
    canNavigateBack: Boolean,
    openUser: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(current.info.title)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            if (user != null) {
                IconButton(onClick = openUser) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = user.username,
                    )
                }
            }
            IconButton(onClick = logout) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                    contentDescription = "logout",
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

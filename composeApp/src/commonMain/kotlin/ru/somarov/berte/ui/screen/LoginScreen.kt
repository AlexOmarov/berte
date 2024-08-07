package ru.somarov.berte.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import ru.somarov.berte.application.viewmodel.AuthViewModel
import ru.somarov.berte.infrastructure.navigation.navigateTo
import ru.somarov.berte.infrastructure.oauth.OIDProvider
import ru.somarov.berte.infrastructure.ui.rememberContext
import ru.somarov.berte.infrastructure.ui.rememberOrientation
import ru.somarov.berte.infrastructure.ui.rememberPlatform
import ru.somarov.berte.ui.Orientation
import ru.somarov.berte.ui.Platform
import ru.somarov.berte.ui.Route
import ru.somarov.library.resources.Res
import ru.somarov.library.resources.apple
import ru.somarov.library.resources.berte
import ru.somarov.library.resources.google
import ru.somarov.library.resources.ok
import ru.somarov.library.resources.telegram
import ru.somarov.library.resources.vk
import ru.somarov.library.resources.yandex

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    controller: NavController,
) {
    val orientation = rememberOrientation()
    when (orientation) {
        Orientation.PORTRAIT -> LoginScreenPortrait(
            viewModel = viewModel,
            controller = controller,
            modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        )

        Orientation.LANDSCAPE -> LoginScreenLandscape(
            viewModel = viewModel,
            controller = controller,
        )
    }
}

@Composable
private fun UsernameOutlinedText(
    username: String,
    modifier: Modifier = Modifier,
    setUsername: (String) -> Unit
) {
    OutlinedTextField(
        value = username,
        onValueChange = setUsername,

        label = {
            Text("Username")
        },
        singleLine = true,
        leadingIcon = {
            IconButton(
                onClick = {
                    setUsername("")
                }, enabled = username.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "очистить поле"
                )
            }
        },
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    )
}

@Composable
private fun PasswordOutlinedText(
    password: String,
    modifier: Modifier = Modifier,
    setPassword: (String) -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    val transformation = remember(showPassword) {
        if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
    }

    OutlinedTextField(
        value = password,
        onValueChange = setPassword,

        label = {
            Text("Password")
        },
        singleLine = true,
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = transformation,
        leadingIcon = {
            IconButton(
                onClick = {
                    setPassword("")
                }, enabled = password.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "очистить поле"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                showPassword = showPassword.not()
            }) {
                Icon(
                    imageVector = if (showPassword)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff,
                    contentDescription = "видимость пароля"
                )
            }
        },
        shape = MaterialTheme.shapes.extraLarge,
    )
}

@Suppress("LongMethod", "CyclomaticComplexMethod") // refactor
@Composable
private fun LoginScreenPortrait(
    viewModel: AuthViewModel,
    controller: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LoginScreenContent(viewModel, controller)
    }
}

@Suppress("LongMethod", "CyclomaticComplexMethod") // refactor
@Composable
private fun LoginScreenLandscape(
    viewModel: AuthViewModel,
    controller: NavController,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize().scrollByPlatform(), contentAlignment = Alignment.Center) {
        Column(Modifier.width(350.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            LoginScreenContent(viewModel, controller)
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun LoginScreenContent(viewModel: AuthViewModel, controller: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Image(
        painter = painterResource(Res.drawable.berte),
        contentDescription = "berte",
        modifier = Modifier.padding(16.dp).size(80.dp)
    )
    Text("Login in Berte", style = MaterialTheme.typography.displayMedium)
    Spacer(Modifier.height(10.dp))
    UsernameOutlinedText(username, Modifier.padding(16.dp).fillMaxWidth()) {
        username = it
    }
    PasswordOutlinedText(password, Modifier.padding(16.dp).fillMaxWidth()) {
        password = it
    }
    Button(
        onClick = {
            viewModel.login(
                username = username, password = password
            )
        }, enabled = username.isNotEmpty() && password.isNotEmpty()
    ) {
        Text("Login with password")
    }
    Spacer(Modifier.height(20.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        LoginProviders(viewModel)
    }
    Spacer(Modifier.height(20.dp))
    TextButton(onClick = { controller.navigateTo(Route.Register) }) {
        Text("Create account")
    }
}

@Composable
private fun Modifier.scrollByPlatform(): Modifier {
    val platform = rememberPlatform()
    return when (platform) {
        Platform.WEB -> this
        Platform.ANDROID -> this.verticalScroll(rememberScrollState())
    }
}

@Composable
internal fun LoginProviders(viewModel: AuthViewModel) {
    val context = rememberContext()

    FilledTonalIconButton(onClick = { viewModel.login(context, OIDProvider.GOOGLE) }) {
        Icon(painter = painterResource(Res.drawable.google), contentDescription = "Google")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, OIDProvider.YANDEX) }) {
        Icon(painter = painterResource(Res.drawable.yandex), contentDescription = "Yandex")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, OIDProvider.APPLE) }) {
        Icon(painter = painterResource(Res.drawable.apple), contentDescription = "Apple")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, OIDProvider.OK) }) {
        Icon(painter = painterResource(Res.drawable.ok), contentDescription = "OK")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, OIDProvider.TELEGRAM) }) {
        Icon(painter = painterResource(Res.drawable.telegram), contentDescription = "Telegram")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, OIDProvider.VK) }) {
        Icon(painter = painterResource(Res.drawable.vk), contentDescription = "VK")
    }
}

package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.somarov.berte.UIScreen
import ru.somarov.berte.application.viewmodel.AppViewModel
import ru.somarov.berte.application.viewmodel.LoginScreenViewModel
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.ui.ErrorBox
import ru.somarov.berte.ui.Orientation
import ru.somarov.berte.ui.WaitBox
import ru.somarov.berte.ui.component.LoginWithProviders
import ru.somarov.berte.ui.rememberOrientation

@Composable
@Suppress("CyclomaticComplexMethod") // refactor
fun LoginScreen(
    navController: NavHostController,
    viewModel: AppViewModel = viewModel(key = "app") { AppViewModel(navController) },
    screenViewModel: LoginScreenViewModel = viewModel { LoginScreenViewModel() }
) {
    val loading by screenViewModel.loginProgress.collectAsState()
    val orientation = rememberOrientation()
    when (val ld = loading) {
        is CommonResult.Empty -> {
            when (orientation) {
                Orientation.PORTRAIT -> LoginScreenContentPortrait(
                    viewModel = viewModel,
                    screenViewModel = screenViewModel,
                    modifier = Modifier.verticalScroll(
                        rememberScrollState()
                    )
                )

                Orientation.LANDSCAPE -> LoginScreenContentLandscape(viewModel, screenViewModel)
            }
        }

        is CommonResult.Error -> ErrorBox(ld.error.message ?: "error")
        is CommonResult.Loading -> WaitBox()
        is CommonResult.Success -> viewModel.navigateTo(UIScreen.Home)
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
private fun LoginScreenContentPortrait(
    viewModel: AppViewModel,
    screenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(20.dp))
        Text("Login in Berte", style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(20.dp))
        UsernameOutlinedText(username, Modifier.padding(16.dp).fillMaxWidth()) {
            username = it
        }
        Spacer(Modifier.height(20.dp))
        PasswordOutlinedText(password, Modifier.padding(16.dp).fillMaxWidth()) {
            password = it
        }
        Button(
            onClick = {
                screenViewModel.viewModelScope.launch {
                    screenViewModel.loginMock(
                        username = username, password = password
                    )
                }
            }, enabled = username.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Login with password")
        }

        Spacer(Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            LoginWithProviders(viewModel)
        }
        Spacer(Modifier.height(20.dp))
        TextButton(onClick = { viewModel.navigateTo(UIScreen.Register) }) {
            Text("Create account")
        }
    }
}

@Suppress("LongMethod", "CyclomaticComplexMethod") // refactor
@Composable
private fun LoginScreenContentLandscape(
    viewModel: AppViewModel,
    screenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.width(350.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Login in Berte", style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(20.dp))
            UsernameOutlinedText(username, Modifier.padding(16.dp).fillMaxWidth()) {
                username = it
            }
            PasswordOutlinedText(password, Modifier.padding(16.dp).fillMaxWidth()) {
                password = it
            }
            Button(
                onClick = {
                    screenViewModel.viewModelScope.launch {
                        screenViewModel.loginMock(
                            username = username, password = password
                        )
                    }
                }, enabled = username.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Login with password")
            }
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LoginWithProviders(viewModel)
            }
            Spacer(Modifier.height(20.dp))

            TextButton(onClick = { viewModel.navigateTo(UIScreen.Register) }) {
                Text("Create account")
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.somarov.berte.UIScreen
import ru.somarov.berte.application.viewmodel.AppViewModel
import ru.somarov.berte.application.viewmodel.LoginScreenViewModel
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.ui.ErrorBox
import ru.somarov.berte.ui.WaitBox

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AppViewModel = viewModel(key = "app") { AppViewModel(navController) },
    screenViewModel: LoginScreenViewModel = viewModel { LoginScreenViewModel() }
) {
    val loading by screenViewModel.loginProgress.collectAsState()

    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val transformation = remember(showPassword) {
        if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
    }

    @Composable
    fun LoginScreenContent(modifier: Modifier = Modifier) {
        Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(Modifier.height(90.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    Text("Username")
                },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text("Password")
                },
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = transformation,
                leadingIcon = (@Composable {
                    IconButton(
                        onClick = {
                            password = ""
                        }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "очистить поле"
                        )
                    }
                }).takeIf { password.isNotEmpty() },
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
                }
            )

            Button(
                onClick = {
                    scope.launch {
                        screenViewModel.loginMock(
                            username = username, password = password
                        )
                    }
                }, enabled = username.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Login with password")
            }

            Spacer(Modifier.height(20.dp))

            Button(onClick = { viewModel.navigateTo(UIScreen.Register) }) {
                Text("Register")
            }
        }
    }

    when (val ld = loading) {
        is CommonResult.Empty -> LoginScreenContent()
        is CommonResult.Error -> ErrorBox(ld.error.message ?: "error")
        is CommonResult.Loading -> WaitBox()
        is CommonResult.Success -> viewModel.navigateTo(UIScreen.Home)
    }
}

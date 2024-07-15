package ru.somarov.berte.application

import androidx.activity.result.contract.ActivityResultContract

data class OAuthProcess<T, V>(
    val processResult: suspend (T) -> Unit,
    val contract: ActivityResultContract<V, T>,
    val launchOptions: V
)

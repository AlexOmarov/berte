package ru.somarov.berte.application

import androidx.activity.result.contract.ActivityResultContract

data class OAuthProcess<T, V>(
    val processResult: (T) -> Unit,
    val contract: ActivityResultContract<V, T>,
    val launchOptions: V
)

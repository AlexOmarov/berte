package ru.somarov.berte.infrastructure.oauth

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import ru.somarov.berte.infrastructure.network.CommonResult

internal suspend fun openAuthForResult(
    context: Any,
    state: OAuthState,
    settings: OAuthSettings
): CommonResult<String> {
    startOAuth(context, state, settings)
    return state.tokenFlow
        .filter {
            it is CommonResult.Success || it is CommonResult.Error
        }
        .first()
}

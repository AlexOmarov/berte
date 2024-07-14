package ru.somarov.berte.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import ru.somarov.berte.application.GoogleProvider
import ru.somarov.berte.application.OIDAndroidProvider
import ru.somarov.berte.application.YandexProvider
import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.infrastructure.oauth.OIDProvider
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.oauth.staticState
import ru.somarov.berte.ui.screen.element.WaitBox

class OAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = Json.decodeFromString<OIDProvider>(intent.getStringExtra("PROVIDER")!!)
        val store = getStaticStateAndReset() ?: TokenStore()

        store.tokenFlow.onEach { finish() }.launchIn(lifecycleScope)

        getProviderByTokenProvider(provider.tokenProvider)
            .authenticate(store, provider.settings, this)

        val view = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { MaterialTheme { WaitBox() } }
        }

        setContentView(view)
    }

    private fun getStaticStateAndReset(): TokenStore? {
        val store = staticState
        staticState = null
        return store
    }

    companion object {
        private val providers = listOf(
            YandexProvider(),
            GoogleProvider()
        )

        fun getProviderByTokenProvider(provider: Token.TokenProvider): OIDAndroidProvider {
            return providers.first { it.getProvider() == provider }
        }
    }
}

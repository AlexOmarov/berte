package ru.somarov.berte.application.compose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex

class BrNavigation() {
    private val _backStack = MutableStateFlow(listOf<UIScreen>())
    private val mutex = Mutex()
    private val _current = MutableStateFlow<UIScreen?>(null)
    val backStack = _backStack.asStateFlow()
    val current = _current.asStateFlow()

    suspend fun navigateTo(uiScreen: UIScreen) {
        mutex.lock()
        try {
            if (uiScreen.screen.root) {
                val current = _backStack.value.toMutableList()
                current.add(uiScreen)
                _backStack.emit(current.toList())
            } else {
                _backStack.emit(emptyList())
            }
            _current.emit(uiScreen)
        } finally {
            mutex.unlock()
        }
    }

    suspend fun navigateBack(): Boolean {
        if (_backStack.value.isEmpty()) return false
        mutex.lock()
        return try {
            val current = _backStack.value.toMutableList()
            val back = current.removeLast()
            _backStack.emit(current.toList())
            _current.emit(back)
            true
        } finally {
            mutex.unlock()
        }
    }
}
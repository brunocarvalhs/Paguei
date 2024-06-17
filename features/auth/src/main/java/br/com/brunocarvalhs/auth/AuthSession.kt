package br.com.brunocarvalhs.auth

import androidx.compose.runtime.Composable


internal object AuthSession {
    lateinit var dependencies: AuthDependencies
    lateinit var theme: (@Composable () -> Unit) -> Unit
}
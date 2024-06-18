package br.com.brunocarvalhs.splash

import androidx.compose.runtime.Composable


internal object SplashSession {
    lateinit var dependencies: SplashDependencies
    lateinit var theme: (@Composable () -> Unit) -> Unit
}
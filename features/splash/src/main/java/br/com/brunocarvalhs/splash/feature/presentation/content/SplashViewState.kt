package br.com.brunocarvalhs.splash.feature.presentation.content

sealed class SplashViewState {
    object Loading : SplashViewState()
    object Session : SplashViewState()
    object NotSession : SplashViewState()
}

package br.com.brunocarvalhs.splash

sealed class SplashViewState {
    object Loading : SplashViewState()
    object Session : SplashViewState()
    object NotSession : SplashViewState()
}

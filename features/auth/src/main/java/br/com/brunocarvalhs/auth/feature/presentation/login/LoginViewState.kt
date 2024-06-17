package br.com.brunocarvalhs.auth.feature.presentation.login

internal sealed class LoginViewState {
    object Loading : LoginViewState()
    object Success : LoginViewState()
    object Default : LoginViewState()

    data class Error(val message: String?) : LoginViewState()
}

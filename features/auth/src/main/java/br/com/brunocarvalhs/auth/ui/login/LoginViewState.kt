package br.com.brunocarvalhs.auth.ui.login

sealed class LoginViewState {
    object Loading : LoginViewState()
    object Success : LoginViewState()
    object Default : LoginViewState()

    data class Error(val message: String?) : LoginViewState()
}

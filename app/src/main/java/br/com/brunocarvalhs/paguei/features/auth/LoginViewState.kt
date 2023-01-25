package br.com.brunocarvalhs.paguei.features.auth

sealed class LoginViewState {
    object Loading : LoginViewState()
    object Success : LoginViewState()
    data class Error(val message: String?) : LoginViewState()
}

package br.com.brunocarvalhs.auth.states

sealed class LoginViewState {
    object Loading : LoginViewState()
    object Success : LoginViewState()
    data class Error(val message: String?) : LoginViewState()
}

package br.com.brunocarvalhs.groups.register

sealed class HomesRegisterViewState {
    object Loading : HomesRegisterViewState()
    object Success : HomesRegisterViewState()
    data class Error(val error: String?) : HomesRegisterViewState()
}

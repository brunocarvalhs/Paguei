package br.com.brunocarvalhs.payflow.features.homes.register

sealed class HomesRegisterViewState {
    object Loading : HomesRegisterViewState()
    object Success : HomesRegisterViewState()
    data class Error(val error: String?) : HomesRegisterViewState()
}

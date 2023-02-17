package br.com.brunocarvalhs.groups.register

sealed class GroupRegisterViewState {
    object Loading : GroupRegisterViewState()
    object Success : GroupRegisterViewState()
    data class Error(val error: String?) : GroupRegisterViewState()
}

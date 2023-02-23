package br.com.brunocarvalhs.groups.edit.state

sealed class EditGroupViewState {
    object Loading : EditGroupViewState()
    object Success : EditGroupViewState()
    data class Error(val message: String?) : EditGroupViewState()
}
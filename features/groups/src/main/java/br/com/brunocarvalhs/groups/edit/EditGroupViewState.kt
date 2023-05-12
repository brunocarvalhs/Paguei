package br.com.brunocarvalhs.groups.edit

sealed class EditGroupViewState {
    object Loading : EditGroupViewState()
    object Success : EditGroupViewState()
    data class Error(val message: String?) : EditGroupViewState()
}
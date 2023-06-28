package br.com.brunocarvalhs.groups.edit

sealed class EditGroupViewState {
    object Loading : EditGroupViewState()
    object Success : EditGroupViewState()
    object MemberSearchSuccess : EditGroupViewState()
    data class Error(val message: String?) : EditGroupViewState()
}
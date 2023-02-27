package br.com.brunocarvalhs.profile.edit_profile

sealed interface EditProfileViewState {
    object Loading : EditProfileViewState
    object Success : EditProfileViewState
    data class Error(val message: String?) : EditProfileViewState
}
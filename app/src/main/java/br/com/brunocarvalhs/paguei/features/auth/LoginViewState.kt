package br.com.brunocarvalhs.paguei.features.auth

import br.com.brunocarvalhs.paguei.model.UserModel

sealed class LoginViewState {
    object Loading : LoginViewState()
    data class Success(val user: UserModel) : LoginViewState()
    data class Error(val message: String?) : LoginViewState()
}

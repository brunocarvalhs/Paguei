package br.com.brunocarvalhs.payflow.features.auth

import br.com.brunocarvalhs.data.model.UserModel

sealed class LoginViewState {
    object Loading : LoginViewState()
    data class Success(val user: UserModel) : LoginViewState()
    data class Error(val message: String?) : LoginViewState()
}

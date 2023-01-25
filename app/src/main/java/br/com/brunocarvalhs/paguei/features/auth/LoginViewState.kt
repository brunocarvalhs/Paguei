package br.com.brunocarvalhs.paguei.features.auth

import br.com.brunocarvalhs.domain.entities.UserEntities

sealed class LoginViewState {
    object Loading : LoginViewState()
    data class Success(val user: UserEntities) : LoginViewState()
    data class Error(val message: String?) : LoginViewState()
}

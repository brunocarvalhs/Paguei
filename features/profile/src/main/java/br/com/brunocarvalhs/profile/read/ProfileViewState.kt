package br.com.brunocarvalhs.profile.read

import br.com.brunocarvalhs.domain.entities.UserEntities

sealed interface ProfileViewState {
    object Loading : ProfileViewState
    data class Success(val user: UserEntities?) : ProfileViewState
    data class Error(val message: String?) : ProfileViewState
}
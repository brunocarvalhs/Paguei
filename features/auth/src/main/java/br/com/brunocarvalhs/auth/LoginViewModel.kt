package br.com.brunocarvalhs.auth

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: Authentication,
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : BaseViewModel<LoginViewState>() {

    fun onSignInResult() {
        viewModelScope.launch {
            try {
                mutableState.value = LoginViewState.Loading
                val session = authService.session()
                session?.let {
                    val user = repository.create(it)
                    sessionManager.login(user, null)
                    mutableState.value = LoginViewState.Success
                }
            } catch (error: Exception) {
                mutableState.value = LoginViewState.Error(error.message)
                throw error
            }
        }
    }

    fun onSession() {
        viewModelScope.launch {
            try {
                mutableState.value = LoginViewState.Loading
                val session = authService.session()
                session?.let {
                    repository.read(it.id)?.let { user ->
                        sessionManager.login(user, null)
                        mutableState.value = LoginViewState.Success
                    }
                }
            } catch (error: Exception) {
                mutableState.value = LoginViewState.Error(error.message)
                throw error
            }
        }
    }
}
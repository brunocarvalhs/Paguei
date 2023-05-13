package br.com.brunocarvalhs.auth

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.usecase.auth.AuthenticateUserUseCase
import br.com.brunocarvalhs.domain.usecase.auth.GetUserFromDatabaseSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val getUserFromSessionUseCase: GetUserFromDatabaseSessionUseCase
) : BaseViewModel<LoginViewState>() {

    fun onSignInResult() {
        viewModelScope.launch {
            mutableState.value = LoginViewState.Loading
            authenticateUserUseCase.invoke().onSuccess {
                it?.let {
                    mutableState.value = LoginViewState.Success
                }.run {
                    mutableState.value = LoginViewState.Error("Authentication failed")
                }
            }.onFailure { error ->
                mutableState.value = LoginViewState.Error(error.message)
            }
        }
    }

    fun onSession() {
        viewModelScope.launch {
            mutableState.value = LoginViewState.Loading
            getUserFromSessionUseCase.invoke().onSuccess {
                it?.let {
                    mutableState.value = LoginViewState.Success
                }.run {
                    mutableState.value = LoginViewState.Error("User not found")
                }
            }.onFailure { error ->
                mutableState.value = LoginViewState.Error(error.message)
            }
        }
    }
}
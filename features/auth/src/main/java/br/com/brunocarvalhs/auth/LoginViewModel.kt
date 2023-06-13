package br.com.brunocarvalhs.auth

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.usecase.auth.AuthenticateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val analyticsService: AnalyticsService,
) : BaseViewModel<LoginViewState>() {

    fun onSignInResult() {
        viewModelScope.launch {
            mutableState.value = LoginViewState.Loading
            authenticateUserUseCase.invoke().onSuccess {
                it?.let {
                    analyticsService.setUserId(it.id)
                    mutableState.value = LoginViewState.Success
                } ?: run {
                    mutableState.value = LoginViewState.Error("Authentication failed")
                }
            }.onFailure { error ->
                mutableState.value = LoginViewState.Error(error.message)
            }
        }
    }
}
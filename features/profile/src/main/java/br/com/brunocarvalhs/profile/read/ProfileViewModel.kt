package br.com.brunocarvalhs.profile.read

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.usecase.auth.GetUserSessionUseCase
import br.com.brunocarvalhs.domain.usecase.auth.LogoutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserSessionUseCase: GetUserSessionUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : BaseViewModel<ProfileViewState>() {

    fun logout(event: () -> Unit) {
        viewModelScope.launch {
            mutableState.value = ProfileViewState.Loading
            logoutUserUseCase.invoke()
                .onSuccess { event() }
                .onFailure { mutableState.value = ProfileViewState.Error(it.message) }
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = ProfileViewState.Loading
            getUserSessionUseCase.invoke()
                .onSuccess { mutableState.value = ProfileViewState.Success(it) }
                .onFailure { mutableState.value = ProfileViewState.Error(it.message) }
        }
    }
}
package br.com.brunocarvalhs.payflow.features.auth

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.payflow.domain.repositories.UserRepository
import br.com.brunocarvalhs.payflow.domain.services.Authentication
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: Authentication<AuthCredential>,
    private val repository: UserRepository,
) : BaseViewModel<LoginViewState>() {

    fun onSignInResult() {
        viewModelScope.launch {
            try {
                mutableState.value = LoginViewState.Loading
                val session = authService.session()
                session?.let {
                    val user = repository.create(it)
                    mutableState.value = LoginViewState.Success(user as UserModel)
                }
            } catch (error: Exception) {
                mutableState.value = LoginViewState.Error(error.message)
            }

        }
    }

    companion object {
        const val RESULT_OK = 123
    }
}
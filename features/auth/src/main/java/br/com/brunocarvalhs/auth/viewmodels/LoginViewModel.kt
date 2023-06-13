package br.com.brunocarvalhs.auth.viewmodels

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.auth.LoginViewState
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.common.Scopes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ILoginViewModel {
    val stateCompose: StateFlow<LoginViewState>

    fun onSignInResult()

    fun startSignInActivity(launcher: ActivityResultLauncher<Intent>)
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: Authentication,
    private val repository: UserRepository,
    private val sessionManager: SessionManager,
) : BaseViewModel<LoginViewState>(), ILoginViewModel {

    private val _state = MutableStateFlow(LoginViewState.Idle)
    override val stateCompose: StateFlow<LoginViewState>
        get() = _state


    override fun onSignInResult() {
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

    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build(),
        AuthUI.IdpConfig.GoogleBuilder().setScopes(listOf(Scopes.PROFILE)).build()
    )

    private val signInIntent = AuthUI.getInstance().createSignInIntentBuilder()
        .setTheme(br.com.brunocarvalhs.paguei.commons.R.style.Theme_Paguei)
        .setAvailableProviders(providers).build()

    override fun startSignInActivity(launcher: ActivityResultLauncher<Intent>) {
        launcher.launch(signInIntent)
    }
}
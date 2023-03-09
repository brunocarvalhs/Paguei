package br.com.brunocarvalhs.auth.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.auth.states.LoginViewState
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.common.Scopes
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ILoginViewModel {
    val stateCompose: StateFlow<LoginViewState>

    fun onSignInResult()

    fun onSession()
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

    override fun onSession() {
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
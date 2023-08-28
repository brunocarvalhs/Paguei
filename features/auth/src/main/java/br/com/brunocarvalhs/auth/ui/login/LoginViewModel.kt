package br.com.brunocarvalhs.auth.ui.login

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.BaseComposeViewModel
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.usecase.auth.AuthenticateUserUseCase
import br.com.brunocarvalhs.paguei.commons.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.common.Scopes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val analyticsService: AnalyticsService,
    private val navigation: Navigation
) : BaseComposeViewModel<LoginViewState>(LoginViewState.Default) {

    private val providers by lazy {
        arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build(),
            AuthUI.IdpConfig.GoogleBuilder().setScopes(listOf(Scopes.PROFILE)).build()
        )
    }

    private val signInIntent by lazy {
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(R.style.Theme_Paguei)
            .setAvailableProviders(providers)
            .build()
    }

    fun signIn(signInLauncher: ManagedActivityResultLauncher<Intent, FirebaseAuthUIAuthenticationResult>) {
        signInLauncher.launch(signInIntent)

        analyticsService.trackEvent(
            AnalyticsService.Events.LOGIN,
            mapOf(),
            LoginFragment::class,
            customAttributes = null
        )
    }

    fun onSignInResult() {
        viewModelScope.launch {
            mutableState.value = LoginViewState.Loading
            authenticateUserUseCase.invoke().onSuccess {
                it?.let {
                    analyticsService.setUserId(it.id)
                    analyticsService.trackEvent(
                        AnalyticsService.Events.LOGIN,
                        mapOf("user_id" to it.id),
                        LoginFragment::class
                    )
                    mutableState.value = LoginViewState.Success
                } ?: run {
                    mutableState.value = LoginViewState.Error("Authentication failed")
                }
            }.onFailure { error ->
                mutableState.value = LoginViewState.Error(error.message)
            }
        }
    }

    fun navigateToHome(navController: NavController) {
        val request = navigation.navigateToCosts()
        navController.navigate(request)

        analyticsService.trackEvent(
            AnalyticsService.Events.SCREEN_VIEWED,
            mapOf(
                Pair("transition_to", "costs_list"),
                Pair("transition_from", "Login")
            ),
            LoginFragment::class,
            customAttributes = null
        )
    }
}
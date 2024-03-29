package br.com.brunocarvalhs.splash

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.BaseComposeViewModel
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.usecase.auth.GetUserFromDatabaseSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserFromSessionUseCase: GetUserFromDatabaseSessionUseCase,
    private val analyticsService: AnalyticsService,
    private val navigation: Navigation
) : BaseComposeViewModel<SplashViewState>(SplashViewState.Loading) {

    fun onSession() {
        viewModelScope.launch {
            mutableState.value = SplashViewState.Loading
            delay(100)
            getUserFromSessionUseCase.invoke().onSuccess {
                it?.let {
                    analyticsService.setUserId(it.id)
                    mutableState.value = SplashViewState.Session
                } ?: kotlin.run {
                    mutableState.value = SplashViewState.NotSession
                }
            }.onFailure {
                mutableState.value = SplashViewState.NotSession
            }
        }
    }

    fun navigateToLogin(navController: NavController) {
        val request = navigation.navigateToLoginRegister()
        navController.navigate(request)
    }

    fun navigateToHome(navController: NavController) {
        val request = navigation.navigateToCosts()
        navController.navigate(request)
    }
}
package br.com.brunocarvalhs.splash

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
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
            delay(2000)
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

    fun navigateToLogin(fragment: Fragment) {
        val request = navigation.navigateToLoginRegister()
        fragment.findNavController().navigate(request)
    }

    fun navigateToHome(fragment: Fragment) {
        val request = navigation.navigateToCosts()
        fragment.findNavController().navigate(request)
    }
}
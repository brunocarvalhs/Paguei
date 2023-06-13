package br.com.brunocarvalhs.splash

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.usecase.auth.GetUserFromDatabaseSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserFromSessionUseCase: GetUserFromDatabaseSessionUseCase,
    private val analyticsService: AnalyticsService,
) : BaseViewModel<SplashViewState>() {

    fun onSession() {
        viewModelScope.launch {
            mutableState.value = SplashViewState.Loading
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
}
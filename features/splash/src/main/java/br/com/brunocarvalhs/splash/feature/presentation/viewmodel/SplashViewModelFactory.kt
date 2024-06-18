package br.com.brunocarvalhs.splash.feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.brunocarvalhs.splash.SplashSession
import br.com.brunocarvalhs.splash.feature.data.repository.SplashRepositoryImpl
import br.com.brunocarvalhs.splash.feature.domain.repository.SplashRepository
import br.com.brunocarvalhs.splash.feature.domain.usecases.GetUserSessionUseCaseImpl

internal class SplashViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository: SplashRepository = SplashRepositoryImpl(
            network = SplashSession.dependencies.network,
        )
        val useCase = GetUserSessionUseCaseImpl(
            repository = repository,
            sessionManager = SplashSession.dependencies.session
        )
        return SplashViewModel(
            getUserFromSessionUseCase = useCase
        ) as T
    }
}
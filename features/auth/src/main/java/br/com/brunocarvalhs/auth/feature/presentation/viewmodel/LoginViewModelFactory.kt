package br.com.brunocarvalhs.auth.feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.brunocarvalhs.auth.AuthSession
import br.com.brunocarvalhs.auth.feature.data.repository.LoginRepositoryImpl
import br.com.brunocarvalhs.auth.feature.domain.usecases.AuthenticateUserUseCaseImpl

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val useCase = AuthenticateUserUseCaseImpl(
            repository = LoginRepositoryImpl(
                networkProvider = AuthSession.dependencies.network
            ),
            sessionManager = AuthSession.dependencies.session
        )
        return LoginViewModel(
            authenticateUserUseCase = useCase
        ) as T
    }
}
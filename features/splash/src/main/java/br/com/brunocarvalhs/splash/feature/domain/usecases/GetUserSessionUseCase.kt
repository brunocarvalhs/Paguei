package br.com.brunocarvalhs.splash.feature.domain.usecases

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.usecase.auth.GetUserFromDatabaseSessionUseCase
import br.com.brunocarvalhs.splash.commons.providers.SplashSessionProvider
import br.com.brunocarvalhs.splash.feature.domain.repository.SplashRepository

internal class GetUserSessionUseCaseImpl(
    private val repository: SplashRepository,
    private val sessionManager: SplashSessionProvider
) : GetUserFromDatabaseSessionUseCase {
    override suspend fun invoke(): Result<UserEntities?> = kotlin.runCatching {
        var user: UserEntities? = null
        sessionManager.get()?.let { session ->
            repository.read(session.id)?.let {
                sessionManager.set(it)
                user = it
            }
        }
        user
    }
}
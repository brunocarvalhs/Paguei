package br.com.brunocarvalhs.auth.feature.domain.usecases

import br.com.brunocarvalhs.auth.commons.providers.AuthSessionProvider
import br.com.brunocarvalhs.auth.feature.domain.repository.LoginRepository
import br.com.brunocarvalhs.domain.entities.UserEntities
import kotlin.runCatching

internal interface AuthenticateUserUseCase {
    suspend fun invoke(): Result<UserEntities?>
}

internal class AuthenticateUserUseCaseImpl(
    private val repository: LoginRepository,
    private val sessionManager: AuthSessionProvider
) : AuthenticateUserUseCase {
    override suspend fun invoke(): Result<UserEntities?> = runCatching {
        val session = sessionManager.get()
        if (session != null) {
            val user = repository.create(session)
            sessionManager.set(user)
            user
        } else {
            null
        }
    }
}
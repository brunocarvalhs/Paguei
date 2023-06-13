package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.AuthenticateUserUseCase
import javax.inject.Inject

class AuthenticateUserUseCaseImpl @Inject constructor(
    private val authService: Authentication,
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : AuthenticateUserUseCase {
    override suspend fun invoke(): Result<UserEntities?> {
        return try {
            val session = authService.session()
            if (session != null) {
                val user = repository.create(session)
                sessionManager.login(user, null)
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
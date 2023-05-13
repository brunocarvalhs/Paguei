package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.GetUserFromDatabaseSessionUseCase
import javax.inject.Inject

class GetUserFromDatabaseSessionUseCaseImpl @Inject constructor(
    private val authService: Authentication,
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : GetUserFromDatabaseSessionUseCase {
    override suspend fun invoke(): Result<UserEntities?> {
        return try {
            val session = authService.session()
            if (session != null) {
                repository.read(session.id)?.let { user ->
                    sessionManager.login(user, null)
                    Result.success(user)
                }
                Result.success(null)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
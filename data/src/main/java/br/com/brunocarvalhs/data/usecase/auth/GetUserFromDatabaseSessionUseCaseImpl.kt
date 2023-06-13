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
            var user: UserEntities? = null
            authService.session()?.let { session ->
                repository.read(session.id)?.let {
                    sessionManager.login(it, null)
                    user = it
                }
            }
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
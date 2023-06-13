package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.UpdateUserUseCase
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: UserRepository
) : UpdateUserUseCase {
    override suspend fun invoke(user: UserEntities): Result<UserEntities> {
        return try {
            val result = repository.update(user)
            sessionManager.login(result, null)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
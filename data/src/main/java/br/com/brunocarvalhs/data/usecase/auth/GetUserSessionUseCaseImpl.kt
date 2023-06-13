package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.GetUserSessionUseCase
import javax.inject.Inject

class GetUserSessionUseCaseImpl @Inject constructor(
    private val sessionManager: SessionManager,
) : GetUserSessionUseCase {
    override fun invoke(): Result<UserEntities?> {
        return try {
            val result = sessionManager.getUser()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
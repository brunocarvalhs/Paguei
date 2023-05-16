package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.LogoutUserUseCase
import javax.inject.Inject

class LogoutUserUseCaseImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val authentication: Authentication
) : LogoutUserUseCase {
    override suspend fun invoke(): Result<Unit> {
        return try {
            if (authentication.logout()) {
                sessionManager.logout()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.DeleteUserUseCase
import javax.inject.Inject

class DeleteUserUseCaseImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val authentication: Authentication,
    private val userRepository: UserRepository
) : DeleteUserUseCase {
    override suspend fun invoke(): Result<Unit> {
        return try {
            sessionManager.getUser()?.let {
                authentication.delete()
                userRepository.delete(it)
                Result.success(Unit)
            }
            Result.failure(Exception("User Session Not Found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
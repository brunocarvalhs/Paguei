package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.usecase.auth.GetUserForEmailUseCase
import javax.inject.Inject

class GetUserForEmailUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : GetUserForEmailUseCase {
    override suspend fun invoke(email: String): Result<UserEntities?> {
        return try {
            val result = repository.search(UserEntities.EMAIL, email)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
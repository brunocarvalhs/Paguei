package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.usecase.auth.GetUserForIdUseCase
import javax.inject.Inject

class GetUserForIdUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : GetUserForIdUseCase {
    override suspend fun invoke(id: String): Result<UserEntities?> {
        return try {
            val result = repository.search(UserEntities.ID, id)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
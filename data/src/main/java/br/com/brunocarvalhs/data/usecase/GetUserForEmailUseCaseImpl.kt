package br.com.brunocarvalhs.data.usecase

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.usecase.GetUserForEmailUseCase
import javax.inject.Inject

class GetUserForEmailUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : GetUserForEmailUseCase {
    override suspend fun invoke(email: String): UserEntities? {
        return repository.search(UserEntities.EMAIL, email)
    }
}
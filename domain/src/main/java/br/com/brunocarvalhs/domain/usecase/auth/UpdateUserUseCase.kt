package br.com.brunocarvalhs.domain.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities

interface UpdateUserUseCase {
    suspend operator fun invoke(user: UserEntities): Result<UserEntities>
}
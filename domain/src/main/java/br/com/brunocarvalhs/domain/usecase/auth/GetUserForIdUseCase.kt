package br.com.brunocarvalhs.domain.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities

interface GetUserForIdUseCase {
    suspend operator fun invoke(id: String): Result<UserEntities?>
}
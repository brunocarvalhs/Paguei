package br.com.brunocarvalhs.domain.usecase

import br.com.brunocarvalhs.domain.entities.UserEntities

interface GetUserForEmailUseCase {
    suspend operator fun invoke(email: String): UserEntities?
}
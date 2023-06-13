package br.com.brunocarvalhs.domain.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities

interface GetUserFromDatabaseSessionUseCase {
    suspend operator fun invoke(): Result<UserEntities?>
}
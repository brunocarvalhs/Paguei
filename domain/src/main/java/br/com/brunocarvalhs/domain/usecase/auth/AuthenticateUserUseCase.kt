package br.com.brunocarvalhs.domain.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities

interface AuthenticateUserUseCase {
    suspend fun invoke(): Result<UserEntities?>
}
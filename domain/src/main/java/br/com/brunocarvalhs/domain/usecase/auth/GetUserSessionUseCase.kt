package br.com.brunocarvalhs.domain.usecase.auth

import br.com.brunocarvalhs.domain.entities.UserEntities

interface GetUserSessionUseCase {
    fun invoke(): Result<UserEntities?>
}
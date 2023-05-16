package br.com.brunocarvalhs.domain.usecase.auth

import br.com.brunocarvalhs.domain.entities.GroupEntities

interface GetGroupSessionUseCase {
    suspend operator fun invoke(group: GroupEntities?): Result<Unit>
}
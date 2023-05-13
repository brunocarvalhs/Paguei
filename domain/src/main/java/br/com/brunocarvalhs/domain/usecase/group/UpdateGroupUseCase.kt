package br.com.brunocarvalhs.domain.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities

interface UpdateGroupUseCase {
    suspend operator fun invoke(group: GroupEntities): Result<Unit>
}
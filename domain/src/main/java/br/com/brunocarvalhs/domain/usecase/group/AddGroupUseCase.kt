package br.com.brunocarvalhs.domain.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities

interface AddGroupUseCase {
    suspend operator fun invoke(group: GroupEntities): Result<Unit>
}
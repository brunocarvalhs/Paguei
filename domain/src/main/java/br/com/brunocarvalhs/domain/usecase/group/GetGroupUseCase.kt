package br.com.brunocarvalhs.domain.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities

interface GetGroupUseCase {
    suspend operator fun invoke(group: GroupEntities): Result<GroupEntities?>
}
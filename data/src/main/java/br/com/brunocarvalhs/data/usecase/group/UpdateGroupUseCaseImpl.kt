package br.com.brunocarvalhs.data.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.usecase.group.UpdateGroupUseCase
import javax.inject.Inject

class UpdateGroupUseCaseImpl @Inject constructor() : UpdateGroupUseCase {
    override suspend fun invoke(group: GroupEntities): Result<Unit> {
        TODO("Not yet implemented")
    }
}
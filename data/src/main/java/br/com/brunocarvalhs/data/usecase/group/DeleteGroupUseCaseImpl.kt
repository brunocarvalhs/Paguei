package br.com.brunocarvalhs.data.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.usecase.group.DeleteGroupUseCase
import javax.inject.Inject

class DeleteGroupUseCaseImpl @Inject constructor() : DeleteGroupUseCase {
    override suspend fun invoke(group: GroupEntities): Result<Unit> {
        TODO("Not yet implemented")
    }
}
package br.com.brunocarvalhs.data.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.usecase.group.AddGroupUseCase
import javax.inject.Inject

class AddGroupUseCaseImpl @Inject constructor() : AddGroupUseCase {
    override suspend fun invoke(group: GroupEntities): Result<Unit> {
        TODO("Not yet implemented")
    }
}
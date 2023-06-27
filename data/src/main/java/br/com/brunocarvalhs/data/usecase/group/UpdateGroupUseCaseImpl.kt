package br.com.brunocarvalhs.data.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.usecase.group.UpdateGroupUseCase
import javax.inject.Inject

class UpdateGroupUseCaseImpl @Inject constructor(
    private val repository: GroupsRepository
) : UpdateGroupUseCase {
    override suspend fun invoke(group: GroupEntities): Result<Unit> {
        return try {
            repository.update(group)
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}
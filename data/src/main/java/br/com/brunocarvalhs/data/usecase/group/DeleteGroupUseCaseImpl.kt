package br.com.brunocarvalhs.data.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.usecase.group.DeleteGroupUseCase
import javax.inject.Inject

class DeleteGroupUseCaseImpl @Inject constructor(
    private val repository: GroupsRepository
) : DeleteGroupUseCase {
    override suspend fun invoke(group: GroupEntities): Result<Unit> {
        return try {
            repository.delete(group)
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}
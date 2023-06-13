package br.com.brunocarvalhs.data.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.usecase.group.GetGroupUseCase
import javax.inject.Inject

class GetGroupUseCaseImpl @Inject constructor(
    private val repository: GroupsRepository
) : GetGroupUseCase {
    override suspend fun invoke(group: GroupEntities): Result<GroupEntities?> {
        return try {
            val result = repository.view(group)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
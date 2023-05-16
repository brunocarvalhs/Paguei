package br.com.brunocarvalhs.data.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.usecase.group.FetchGroupsUseCase
import javax.inject.Inject

class FetchGroupsUseCaseImpl @Inject constructor(
    private val groupsRepository: GroupsRepository
) : FetchGroupsUseCase {
    override suspend fun invoke(): Result<List<GroupEntities>> {
        return try {
            val result = groupsRepository.list()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
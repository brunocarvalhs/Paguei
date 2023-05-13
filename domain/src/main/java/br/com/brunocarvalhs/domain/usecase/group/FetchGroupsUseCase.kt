package br.com.brunocarvalhs.domain.usecase.group

import br.com.brunocarvalhs.domain.entities.GroupEntities

interface FetchGroupsUseCase {
    suspend operator fun invoke(): Result<List<GroupEntities>>
}
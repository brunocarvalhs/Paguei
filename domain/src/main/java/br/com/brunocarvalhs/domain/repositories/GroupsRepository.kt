package br.com.brunocarvalhs.domain.repositories

import br.com.brunocarvalhs.domain.entities.GroupEntities

interface GroupsRepository {
    suspend fun add(homes: GroupEntities)
    suspend fun list(): List<GroupEntities>
    suspend fun update(homes: GroupEntities): GroupEntities
    suspend fun delete(homes: GroupEntities)
    suspend fun view(homes: GroupEntities): GroupEntities?
}
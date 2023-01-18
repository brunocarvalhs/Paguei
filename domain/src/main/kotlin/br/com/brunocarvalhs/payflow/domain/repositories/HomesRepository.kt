package br.com.brunocarvalhs.paguei.domain.repositories

import br.com.brunocarvalhs.paguei.domain.entities.HomesEntities

interface HomesRepository {
    suspend fun add(homes: HomesEntities)
    suspend fun list(): List<HomesEntities>
    suspend fun update(homes: HomesEntities): HomesEntities
    suspend fun delete(homes: HomesEntities)
    suspend fun view(homes: HomesEntities): HomesEntities?
}
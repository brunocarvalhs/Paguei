package br.com.brunocarvalhs.domain.repositories

import br.com.brunocarvalhs.domain.entities.CostEntities

interface CostsRepository {
    suspend fun add(cost: CostEntities)
    suspend fun list(): List<CostEntities>
    suspend fun update(cost: CostEntities): CostEntities
    suspend fun delete(cost: CostEntities)
    suspend fun view(cost: CostEntities): CostEntities?
    suspend fun moveDocumentToCollection(
        cost: CostEntities,
        targetCollection: String,
        targetId: String
    )
}
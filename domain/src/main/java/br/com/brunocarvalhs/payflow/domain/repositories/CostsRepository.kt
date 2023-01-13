package br.com.brunocarvalhs.payflow.domain.repositories

import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities

interface CostsRepository {
    suspend fun add(cost: CostsEntities)
    suspend fun list(): List<CostsEntities>
    suspend fun update(cost: CostsEntities): CostsEntities
    suspend fun delete(cost: CostsEntities)
    suspend fun view(cost: CostsEntities): CostsEntities?
}
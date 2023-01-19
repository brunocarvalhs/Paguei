package br.com.brunocarvalhs.domain.repositories

import br.com.brunocarvalhs.domain.entities.UserEntities

interface UserRepository {
    suspend fun isUserExist(user: UserEntities): Boolean
    suspend fun create(user: UserEntities): UserEntities
    suspend fun read(id: String): UserEntities?
    suspend fun update(user: UserEntities): UserEntities
    suspend fun delete(user: UserEntities)
}
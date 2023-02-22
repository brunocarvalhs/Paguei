package br.com.brunocarvalhs.domain.services

import br.com.brunocarvalhs.domain.entities.UserEntities

interface Authentication {
    suspend fun logout(): Boolean
    suspend fun session(): UserEntities?
    suspend fun login(credential: Any?): UserEntities?

    suspend fun delete(): Boolean
}
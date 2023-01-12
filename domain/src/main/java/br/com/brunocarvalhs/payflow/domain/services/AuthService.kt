package br.com.brunocarvalhs.payflow.domain.services

import br.com.brunocarvalhs.payflow.domain.entities.UserEntities

interface AuthService<T : Any> {
    suspend fun logout(): Boolean
    suspend fun session(): UserEntities?
    suspend fun login(credential: T?): UserEntities?
}
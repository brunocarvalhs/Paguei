package br.com.brunocarvalhs.auth.feature.domain.repository

import br.com.brunocarvalhs.domain.entities.UserEntities

interface LoginRepository {
    suspend fun create(user: UserEntities): UserEntities
}
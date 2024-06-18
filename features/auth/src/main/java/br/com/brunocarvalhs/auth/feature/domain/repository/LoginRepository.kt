package br.com.brunocarvalhs.auth.feature.domain.repository

import br.com.brunocarvalhs.domain.entities.UserEntities

internal interface LoginRepository {
    suspend fun create(user: HashMap<String, Any>): HashMap<String, Any>
}
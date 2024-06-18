package br.com.brunocarvalhs.splash.feature.domain.repository

import br.com.brunocarvalhs.domain.entities.UserEntities

interface SplashRepository {
    suspend fun read(id: String): UserEntities?
}
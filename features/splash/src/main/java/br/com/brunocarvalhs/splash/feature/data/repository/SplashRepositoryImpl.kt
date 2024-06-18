package br.com.brunocarvalhs.splash.feature.data.repository

import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.splash.commons.providers.SplashNetworkProvider
import br.com.brunocarvalhs.splash.feature.domain.repository.SplashRepository

class SplashRepositoryImpl(private val network: SplashNetworkProvider) : SplashRepository {
    override suspend fun read(id: String): UserEntities? {
        return network.request<UserEntities>(
            url = "users",
            method = "GET",
            query = mapOf("id" to id),
        ).getOrNull()
    }
}

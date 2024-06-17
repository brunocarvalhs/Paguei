package br.com.brunocarvalhs.auth.feature.data.repository

import br.com.brunocarvalhs.auth.commons.providers.AuthNetworkProvider
import br.com.brunocarvalhs.auth.feature.domain.repository.LoginRepository
import br.com.brunocarvalhs.domain.entities.UserEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class LoginRepositoryImpl(
    private val networkProvider: AuthNetworkProvider,
) : LoginRepository {
    override suspend fun create(user: UserEntities): UserEntities = withContext(Dispatchers.IO) {
        try {
            val payload = user.toJson()
            networkProvider.request(
                url = "users",
                method = "POST",
                body = payload
            )
            return@withContext user
        } catch (error: Exception) {
            throw error
        }
    }
}
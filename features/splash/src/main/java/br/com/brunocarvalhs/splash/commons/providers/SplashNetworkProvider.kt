package br.com.brunocarvalhs.splash.commons.providers

import java.io.Serializable

interface SplashNetworkProvider {
    suspend fun <T: Serializable> request(
        url: String,
        method: String,
        body: String? = null,
        headers: Map<String, String> = emptyMap(),
        query: Map<String, String> = emptyMap()
    ): Result<T>
}

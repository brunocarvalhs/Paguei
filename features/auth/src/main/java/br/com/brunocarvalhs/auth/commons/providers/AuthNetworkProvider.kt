package br.com.brunocarvalhs.auth.commons.providers

interface AuthNetworkProvider {
    suspend fun request(
        url: String,
        method: String,
        body: String? = null,
        headers: Map<String, String> = emptyMap()
    ): Result<Unit>
}
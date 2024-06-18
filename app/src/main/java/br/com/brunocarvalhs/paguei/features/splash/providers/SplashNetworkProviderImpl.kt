package br.com.brunocarvalhs.paguei.features.splash.providers

import br.com.brunocarvalhs.auth.commons.providers.AuthNetworkProvider
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.data.repositories.UserRepositoryImpl
import br.com.brunocarvalhs.splash.commons.providers.SplashNetworkProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class SplashNetworkProviderImpl : SplashNetworkProvider {

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Serializable> request(
        url: String,
        method: String,
        body: String?,
        headers: Map<String, String>,
        query: Map<String, String>
    ): Result<T> = kotlin.runCatching {
        val id: String? = query["id"]
        id?.let {
            UserRepositoryImpl(
                Firebase.firestore,
            ).read(id) as T
        } ?: throw Exception("id is null")
    }
}
package br.com.brunocarvalhs.paguei.features.providers

import br.com.brunocarvalhs.auth.commons.providers.AuthNetworkProvider
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.data.repositories.UserRepositoryImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthNetworkProviderImpl : AuthNetworkProvider {
    override suspend fun request(
        url: String,
        method: String,
        body: String?,
        headers: Map<String, String>
    ): Result<Unit> = kotlin.runCatching {
        body?.let {
            val user = UserModel.fromJson(body)
            UserRepositoryImpl(
                database = Firebase.firestore,
            ).create(user)
        } ?:throw Exception("Body is null")
    }
}
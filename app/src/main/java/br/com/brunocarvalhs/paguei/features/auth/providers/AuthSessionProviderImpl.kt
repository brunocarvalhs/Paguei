package br.com.brunocarvalhs.paguei.features.auth.providers

import br.com.brunocarvalhs.auth.commons.providers.AuthSessionProvider
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.data.services.SessionManagerService
import br.com.brunocarvalhs.domain.entities.UserEntities

class AuthSessionProviderImpl : AuthSessionProvider {

    private val session by lazy { SessionManagerService() }

    override fun get(): HashMap<String, Any>? {
        val user = session.getUser()?.let {
            HashMap(it.toMap())
        }
        return user
    }

    override fun set(user: HashMap<String, Any>) {
        return session.login(user = UserModel.fromMap(user), token = null)
    }
}
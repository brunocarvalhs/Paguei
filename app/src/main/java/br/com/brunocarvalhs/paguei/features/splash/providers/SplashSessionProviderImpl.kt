package br.com.brunocarvalhs.paguei.features.splash.providers

import br.com.brunocarvalhs.auth.commons.providers.AuthSessionProvider
import br.com.brunocarvalhs.data.services.SessionManagerService
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.splash.commons.providers.SplashSessionProvider

class SplashSessionProviderImpl : SplashSessionProvider {

    private val session by lazy { SessionManagerService() }

    override fun get(): UserEntities? {
        return session.getUser()
    }

    override fun set(user: UserEntities) {
        return session.login(user = user, token = null)
    }
}
package br.com.brunocarvalhs.splash.commons.providers

import br.com.brunocarvalhs.domain.entities.UserEntities

interface SplashSessionProvider {
    fun set(user: UserEntities)
    fun get(): UserEntities?
}

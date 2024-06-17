package br.com.brunocarvalhs.auth.commons.providers

import br.com.brunocarvalhs.domain.entities.UserEntities

interface AuthSessionProvider {
    fun get(): UserEntities?
    fun set(user: UserEntities): Unit
}
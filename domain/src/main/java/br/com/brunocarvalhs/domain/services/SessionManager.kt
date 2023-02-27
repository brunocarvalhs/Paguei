package br.com.brunocarvalhs.domain.services

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities

interface SessionManager {
    fun isLoggedIn(): Boolean
    fun login(user: UserEntities, token: String?)
    fun logout()
    fun getUser(): UserEntities?
    fun getToken(): String?
    fun isGroupSession(): Boolean
    fun sessionGroup(group: GroupEntities?)
    fun getGroup(): GroupEntities?
}
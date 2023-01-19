package br.com.brunocarvalhs.paguei.domain.services

import br.com.brunocarvalhs.paguei.domain.entities.UserEntities

interface SessionManager {
    fun isLoggedIn(): Boolean
    fun login(user: UserEntities, token: String?)
    fun logout()
    fun getUser(): UserEntities?
    fun getToken(): String?
}
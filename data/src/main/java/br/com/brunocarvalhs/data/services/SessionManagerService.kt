package br.com.brunocarvalhs.data.services

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerService @Inject constructor() : SessionManager {
    private var user: UserEntities? = null
    private var token: String? = null
    private var isLoggedIn: Boolean = false
    private var group: GroupEntities? = null

    override fun isLoggedIn(): Boolean = isLoggedIn

    override fun isGroupSession(): Boolean =
        isLoggedIn && group != null && group?.members?.contains(user?.id) == true

    override fun login(user: UserEntities, token: String?) {
        this.user = user
        this.token = token
        this.isLoggedIn = true
    }

    override fun logout() {
        this.user = null
        this.token = null
        this.isLoggedIn = false
    }

    override fun getUser(): UserEntities? = user

    override fun getToken(): String? = token
    override fun sessionGroup(group: GroupEntities?) {
        this.group = group
    }

    override fun getGroup(): GroupEntities? = group
}
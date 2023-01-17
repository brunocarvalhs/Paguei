package br.com.brunocarvalhs.data.services

import android.content.Context
import br.com.brunocarvalhs.payflow.domain.entities.UserEntities
import br.com.brunocarvalhs.payflow.domain.services.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerService @Inject constructor(
    @ApplicationContext private val context: Context,
) : SessionManager {
    private var user: UserEntities? = null
    private var token: String? = null
    private var isLoggedIn: Boolean = false

    override fun isLoggedIn(): Boolean = isLoggedIn

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
}
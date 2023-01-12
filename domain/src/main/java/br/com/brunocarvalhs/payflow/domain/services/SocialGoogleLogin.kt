package br.com.brunocarvalhs.payflow.domain.services

import br.com.brunocarvalhs.payflow.domain.entities.UserEntities

interface SocialGoogleLogin {
    fun <T> signIn(context: T)
    suspend fun <T> onResult(requestCode: Int, data: T?): Result<UserEntities>
}
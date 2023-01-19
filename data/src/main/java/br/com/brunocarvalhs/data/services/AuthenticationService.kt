package br.com.brunocarvalhs.data.services

import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationService @Inject constructor(
    private val auth: FirebaseAuth,
    private val sessionManager: SessionManager,
) : Authentication<AuthCredential> {

    override suspend fun logout(): Boolean = withContext(Dispatchers.IO) {
        try {
            auth.signOut()
            sessionManager.logout()
            return@withContext true
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    override suspend fun session(): UserEntities? = withContext(Dispatchers.IO) {
        val session = auth.currentUser
        session?.let {
            val user = UserModel.fromFirebaseAuth(it)
            sessionManager.login(user = user, token = null)
            return@withContext user
        }
        return@withContext null
    }

    override suspend fun login(credential: AuthCredential?): UserEntities? =
        withContext(Dispatchers.IO) {
            try {
                val result = credential?.let {
                    val result = auth.signInWithCredential(credential).await()
                    val session = result.user ?: throw Exception()
                    val user = UserModel.fromFirebaseAuth(session)
                    sessionManager.login(user = user, token = null)
                    return@let user
                }
                return@withContext result
            } catch (error: Exception) {
                throw Exception(error)
            }
        }

    fun UserModel.Companion.fromFirebaseAuth(firebaseUser: FirebaseUser) = UserModel(
        id = firebaseUser.uid,
        name = firebaseUser.displayName,
        email = firebaseUser.email,
        phoneNumber = firebaseUser.phoneNumber,
        photoUrl = firebaseUser.photoUrl.toString(),
    )
}
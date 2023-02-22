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
) : Authentication {

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

    override suspend fun delete(): Boolean = withContext(Dispatchers.IO) {
        try {
            sessionManager.logout()
            return@withContext auth.currentUser?.delete()?.isSuccessful ?: false
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    override suspend fun login(credential: Any?): UserEntities? =
        withContext(Dispatchers.IO) {
            try {
                if (credential is AuthCredential) {
                    val result = auth.signInWithCredential(credential).await()
                    val session = result.user ?: throw Exception()
                    val user = UserModel.fromFirebaseAuth(session)
                    sessionManager.login(user = user, token = null)
                    return@withContext user
                }
                return@withContext null
            } catch (error: Exception) {
                throw Exception(error)
            }
        }

    private fun UserModel.Companion.fromFirebaseAuth(firebaseUser: FirebaseUser) = UserModel(
        id = firebaseUser.uid,
        name = firebaseUser.displayName,
        email = firebaseUser.email,
        photoUrl = firebaseUser.photoUrl.toString(),
    )
}
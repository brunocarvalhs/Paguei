package br.com.brunocarvalhs.data.services

import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.payflow.domain.entities.UserEntities
import br.com.brunocarvalhs.payflow.domain.services.AuthService
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthService<AuthCredential> {

    override suspend fun logout(): Boolean = withContext(Dispatchers.IO) {
        try {
            auth.signOut()
            return@withContext true
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    override suspend fun session(): UserEntities? = withContext(Dispatchers.IO) {
        val session = auth.currentUser
        session?.let { return@withContext UserModel.fromFirebaseAuth(it) }
        return@withContext null
    }

    override suspend fun login(credential: AuthCredential?): UserEntities? =
        withContext(Dispatchers.IO) {
            try {
                val result = credential?.let {
                    val result = auth.signInWithCredential(credential).await()
                    val user = result.user ?: throw Exception()
                    UserModel.fromFirebaseAuth(user)
                }
                return@withContext result
            } catch (error: Exception) {
                throw Exception(error)
            }
        }

    private fun UserModel.Companion.fromFirebaseAuth(firebaseUser: FirebaseUser) = UserModel(
        id = firebaseUser.uid,
        name = firebaseUser.displayName,
        email = firebaseUser.email,
        phoneNumber = firebaseUser.phoneNumber,
        photoUrl = firebaseUser.photoUrl.toString(),
    )
}
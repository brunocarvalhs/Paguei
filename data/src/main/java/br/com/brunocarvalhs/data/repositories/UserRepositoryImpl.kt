package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    database: FirebaseFirestore
) : UserRepository {

    private val collection = database.collection(UserModel.COLLECTION)

    override suspend fun isUserExist(user: UserEntities): Boolean = withContext(Dispatchers.IO) {
        return@withContext collection.document(user.id).get().await()
            .exists()
    }

    override suspend fun create(user: UserEntities): UserEntities = withContext(Dispatchers.IO) {
        try {
            collection.document(user.id).set(user.toMap()).await()
            return@withContext user
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun read(id: String): UserEntities? = withContext(Dispatchers.IO) {
        try {
            val result = collection.document(id).get().await()
            return@withContext result.toObject(UserModel::class.java)
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun update(user: UserEntities): UserEntities = withContext(Dispatchers.IO) {
        try {
            collection.document(user.id).update(user.toMap()).await()
            return@withContext user
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun delete(user: UserEntities) = withContext(Dispatchers.IO) {
        try {
            collection.document(user.id).delete().await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }
}

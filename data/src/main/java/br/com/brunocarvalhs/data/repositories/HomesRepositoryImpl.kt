package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.HomesModel
import br.com.brunocarvalhs.domain.entities.HomesEntities
import br.com.brunocarvalhs.domain.repositories.HomesRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomesRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore,
    private val sessionManager: SessionManager,
) : HomesRepository {

    override suspend fun add(homes: HomesEntities) = withContext(Dispatchers.IO) {
        try {
            database.collection(HomesModel.COLLECTION).document(homes.id).set(homes.toMap()).await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun list(): List<HomesEntities> = withContext(Dispatchers.IO) {
        try {
            return@withContext sessionManager.getUser()?.let {
                val result = database.collection(HomesModel.COLLECTION)
                    .whereArrayContainsAny(HomesModel.MEMBERS, listOf(sessionManager.getUser()?.id))
                    .get()
                    .await()
                result.map { it.toObject(HomesModel::class.java) }
            } ?: emptyList()
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun update(homes: HomesEntities): HomesEntities = withContext(Dispatchers.IO) {
        try {
            database.collection(HomesModel.COLLECTION)
                .document(homes.id)
                .update(homes.toMap())
                .await()
            return@withContext homes
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun delete(homes: HomesEntities) = withContext(Dispatchers.IO) {
        try {
            database.collection(HomesModel.COLLECTION).document(homes.id).delete().await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun view(homes: HomesEntities): HomesEntities? = withContext(Dispatchers.IO) {
        try {
            return@withContext sessionManager.getUser()?.let {
                val result = database.collection(HomesModel.COLLECTION)
                    .document(it.id).get().await()
                result.toObject(HomesModel::class.java)
            }
        } catch (error: Exception) {
            throw Exception(error)
        }
    }
}
package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.GroupsModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
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

    override suspend fun add(homes: GroupEntities) = withContext(Dispatchers.IO) {
        try {
            database.collection(GroupsModel.COLLECTION).document(homes.id).set(homes.toMap()).await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun list(): List<GroupEntities> = withContext(Dispatchers.IO) {
        try {
            return@withContext sessionManager.getUser()?.let {
                val result = database.collection(GroupsModel.COLLECTION)
                    .whereArrayContainsAny(GroupsModel.MEMBERS, listOf(sessionManager.getUser()?.id))
                    .get()
                    .await()
                result.map { it.toObject(GroupsModel::class.java) }
            } ?: emptyList()
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun update(homes: GroupEntities): GroupEntities = withContext(Dispatchers.IO) {
        try {
            database.collection(GroupsModel.COLLECTION)
                .document(homes.id)
                .update(homes.toMap())
                .await()
            return@withContext homes
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun delete(homes: GroupEntities) = withContext(Dispatchers.IO) {
        try {
            database.collection(GroupsModel.COLLECTION).document(homes.id).delete().await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun view(homes: GroupEntities): GroupEntities? = withContext(Dispatchers.IO) {
        try {
            return@withContext sessionManager.getUser()?.let {
                val result = database.collection(GroupsModel.COLLECTION)
                    .document(it.id).get().await()
                result.toObject(GroupsModel::class.java)
            }
        } catch (error: Exception) {
            throw Exception(error)
        }
    }
}
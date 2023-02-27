package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.GroupsModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(
    database: FirebaseFirestore,
    private val sessionManager: SessionManager,
) : GroupsRepository {

    private val collection = database.collection(GroupEntities.COLLECTION)

    override suspend fun add(homes: GroupEntities) = withContext(Dispatchers.IO) {
        try {
            collection.document(homes.id).set(homes.toMap()).await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun list(): List<GroupEntities> = withContext(Dispatchers.IO) {
        try {
            return@withContext sessionManager.getUser()?.let {
                val result = collection
                    .whereArrayContainsAny(GroupEntities.MEMBERS, listOf(sessionManager.getUser()?.id))
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
            collection
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
            collection.document(homes.id).delete().await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun view(homes: GroupEntities): GroupEntities? = withContext(Dispatchers.IO) {
        try {
            return@withContext sessionManager.getUser()?.let {
                val result = collection
                    .document(it.id).get().await()
                result.toObject(GroupsModel::class.java)
            }
        } catch (error: Exception) {
            throw Exception(error)
        }
    }
}
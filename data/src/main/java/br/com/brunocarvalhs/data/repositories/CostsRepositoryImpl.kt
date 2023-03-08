package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CostsRepositoryImpl @Inject constructor(
    database: FirebaseFirestore,
    private val sessionManager: SessionManager,
) : CostsRepository {

    private val routerCollection =
        if (sessionManager.isGroupSession())
            "${GroupEntities.COLLECTION}/${sessionManager.getGroup()?.id}/${CostEntities.COLLECTION}"
        else
            "${UserEntities.COLLECTION}/${sessionManager.getUser()?.id}/${CostEntities.COLLECTION}"

    private val collection = database.collection(routerCollection)

    override suspend fun add(cost: CostEntities) = withContext(Dispatchers.IO) {
        try {
            collection.document(cost.id).set(cost.toMap()).await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun list(): List<CostEntities> = withContext(Dispatchers.IO) {
        try {
            val result = collection.get().await()
            return@withContext result.map { it.toObject(CostsModel::class.java) }
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun view(cost: CostEntities): CostEntities? = withContext(Dispatchers.IO) {
        try {
            sessionManager.getUser()?.let {
                val result = collection.document(it.id).get().await()
                return@withContext result.toObject(CostsModel::class.java)
            }
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    override suspend fun update(cost: CostEntities): CostEntities = withContext(Dispatchers.IO) {
        try {
            collection.document(cost.id).update(cost.toMap()).await()
            return@withContext cost
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun delete(cost: CostEntities) = withContext(Dispatchers.IO) {
        try {
            collection.document(cost.id).delete().await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }
}
package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CostsRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore,
    private val sessionManager: SessionManager,
) : CostsRepository {

    private val routerCollection =
        "${UserModel.COLLECTION}/${sessionManager.getUser()?.id}/${CostsModel.COLLECTION}"

    override suspend fun add(cost: CostsEntities) = withContext(Dispatchers.IO) {
        try {
            database.collection(routerCollection).document(cost.id).set(cost.toMap()).await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun list(): List<CostsEntities> = withContext(Dispatchers.IO) {
        try {
            val result = database.collection(routerCollection).get().await()
            return@withContext result.map { it.toObject(CostsModel::class.java) }
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun view(cost: CostsEntities): CostsEntities? = withContext(Dispatchers.IO) {
        try {
            sessionManager.getUser()?.let {
                val result = database.collection(routerCollection).document(it.id).get().await()
                return@withContext result.toObject(CostsModel::class.java)
            }
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    override suspend fun update(cost: CostsEntities): CostsEntities = withContext(Dispatchers.IO) {
        try {
            database.collection(routerCollection).document(cost.id).update(cost.toMap()).await()
            return@withContext cost
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun delete(cost: CostsEntities) = withContext(Dispatchers.IO) {
        try {
            database.collection(routerCollection).document(cost.id).delete().await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }
}
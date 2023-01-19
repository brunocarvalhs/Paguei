package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.paguei.domain.entities.CostsEntities
import br.com.brunocarvalhs.paguei.domain.services.SessionManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CostsRepositoryImplTest {

    @Mock
    lateinit var database: FirebaseFirestore

    @Mock
    lateinit var sessionManager: SessionManager

    @Mock
    lateinit var cost: CostsEntities

    @Mock
    lateinit var userModel: UserModel

    @Mock
    lateinit var documentSnapshot: Task<DocumentSnapshot>

    @Mock
    lateinit var taskVoid: Task<Void>

    lateinit var costsRepository: CostsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        costsRepository = CostsRepositoryImpl(database, sessionManager)
    }

    @Test
    fun testAdd() {
        runBlocking {
            Mockito.`when`(
                database.collection(Mockito.anyString()).document(Mockito.anyString())
                    .set(Mockito.isNull())
            ).thenReturn(taskVoid)
            costsRepository.add(cost)
        }
    }

    @Test
    fun testView() {
        runBlocking {
            Mockito.`when`(sessionManager.getUser()).thenReturn(userModel)
            Mockito.`when`(
                database.collection(Mockito.anyString()).document(Mockito.anyString()).get()
            ).thenReturn(documentSnapshot)
            Mockito.`when`(documentSnapshot.result.toObject(CostsModel::class.java))
                .thenReturn(cost as CostsModel)
            val result = costsRepository.view(cost)
            assertEquals(cost, result)
        }
    }

    @Test
    fun testViewWithError() {
        runBlocking {
            Mockito.`when`(sessionManager.getUser()).thenReturn(null)
            val result = costsRepository.view(cost)
            assertNull(result)
        }
    }

    @Test
    fun testUpdate() {
        runBlocking {
            Mockito.`when`(
                database.collection(Mockito.anyString()).document(Mockito.anyString())
                    .update(Mockito.anyMap())
            ).thenReturn(taskVoid)
            val result = costsRepository.update(cost)
            assertEquals(cost, result)
        }
    }

    @Test
    fun testDelete() {
        runBlocking {
            Mockito.`when`(
                database.collection(Mockito.anyString()).document(Mockito.anyString()).delete()
            ).thenReturn(taskVoid)
            costsRepository.delete(cost)
            Mockito.verify(
                database.collection(Mockito.anyString()).document(Mockito.anyString()),
                Mockito.times(1)
            ).delete()
        }
    }
}


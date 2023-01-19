package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.HomesModel
import br.com.brunocarvalhs.paguei.domain.entities.HomesEntities
import br.com.brunocarvalhs.paguei.domain.entities.UserEntities
import br.com.brunocarvalhs.paguei.domain.services.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomesRepositoryImplTest {

    @Mock
    private lateinit var database: FirebaseFirestore

    @Mock
    private lateinit var sessionManager: SessionManager

    @Mock
    private lateinit var homes: HomesEntities

    @Mock
    private lateinit var user: UserEntities

    private lateinit var repository: HomesRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = HomesRepositoryImpl(database, sessionManager)
        `when`(sessionManager.getUser()).thenReturn(user)
        `when`(user.id).thenReturn("user_id")
    }

    @Test
    fun add() {
        runBlocking {
            repository.add(homes)
            verify(database).collection(HomesModel.COLLECTION).document(homes.id).set(homes.toMap())
        }
    }

    @Test
    fun update() {
        runBlocking {
            repository.update(homes)
            verify(database).collection(HomesModel.COLLECTION).document(homes.id)
                .update(homes.toMap())
        }
    }

    @Test
    fun delete() {
        runBlocking {
            repository.delete(homes)
            verify(database).collection(HomesModel.COLLECTION).document(homes.id).delete()
        }
    }
}


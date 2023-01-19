package br.com.brunocarvalhs.data.repositories

import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.paguei.domain.entities.UserEntities
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UserRepositoryImplTest {

    @Mock
    private lateinit var database: FirebaseFirestore

    @Mock
    private lateinit var user: UserEntities

    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = UserRepositoryImpl(database)
    }

    @Test
    fun create() {
        runBlocking {
            repository.create(user)
            verify(database).collection(UserModel.COLLECTION).document(user.id).set(user.toMap())
        }
    }

    @Test
    fun update() {
        runBlocking {
            repository.update(user)
            verify(database).collection(UserModel.COLLECTION).document(user.id).update(user.toMap())
        }
    }

    @Test
    fun delete() {
        runBlocking {
            repository.delete(user)
            verify(database).collection(UserModel.COLLECTION).document(user.id).delete()
        }
    }
}

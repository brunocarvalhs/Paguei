package br.com.brunocarvalhs.data.repository

import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.data.repositories.UserRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@HiltAndroidTest
class UserRepositoryImplTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var userRepository: UserRepositoryImpl

    @Inject
    lateinit var database: FirebaseFirestore

    @Before
    fun setUp() {
        hiltRule.inject()
        userRepository = UserRepositoryImpl(database)
    }

    @Test
    fun testCreate() = runBlocking {
        val user = UserModel(id = "123", name = "John")
        val result = userRepository.create(user)
        assertEquals(user, result)
    }

    @Test
    fun testRead() = runBlocking {
        val user = UserModel(id = "123", name = "John")
        Mockito.`when`(
            database.collection(UserModel.COLLECTION)
                .document(user.id).get()
                .await().toObject(UserModel::class.java)
        ).thenReturn(user)
        val result = userRepository.read(user.id)
        assertEquals(user, result)
    }

    @Test
    fun testUpdate() = runBlocking {
        val user = UserModel(id = "123", name = "John")
        val result = userRepository.update(user)
        assertEquals(user, result)
    }

    @Test
    fun testDelete() = runBlocking {
        val user = UserModel(id = "123", name = "John")
        userRepository.delete(user)
    }
}

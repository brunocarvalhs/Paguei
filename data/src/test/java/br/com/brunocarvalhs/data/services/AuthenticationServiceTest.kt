package br.com.brunocarvalhs.data.services

import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.paguei.domain.services.SessionManager
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
class AuthenticationServiceTest {

    @Mock
    lateinit var auth: FirebaseAuth

    @Mock
    lateinit var sessionManager: SessionManager

    @Mock
    lateinit var authCredential: AuthCredential

    @Mock
    lateinit var firebaseUser: FirebaseUser

    @Mock
    lateinit var userModel: UserModel

    lateinit var authenticationService: AuthenticationService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authenticationService = AuthenticationService(auth, sessionManager)
    }

    @Test
    fun testLogout() {
        runBlocking {
            Mockito.`when`(auth.signOut()).thenReturn(Unit)
            val logout = authenticationService.logout()
            assertEquals(true, logout)
        }
    }

    @Test
    fun testSession() {
        runBlocking {
            Mockito.`when`(auth.currentUser).thenReturn(firebaseUser)
            val session = authenticationService.session()
            assertEquals(userModel, session)
        }
    }

    @Test
    fun testLoginWithError() {
        runBlocking {
            authCredential.let {
                Mockito.`when`(auth.signInWithCredential(authCredential)).thenThrow(Exception())
                val login = authenticationService.login(authCredential)
                assertNull(login)
            }
        }
    }
}


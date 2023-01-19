package br.com.brunocarvalhs.data.di

import android.content.Context
import br.com.brunocarvalhs.data.repositories.CostsRepositoryImpl
import br.com.brunocarvalhs.data.repositories.HomesRepositoryImpl
import br.com.brunocarvalhs.data.repositories.UserRepositoryImpl
import br.com.brunocarvalhs.data.services.AuthenticationService
import br.com.brunocarvalhs.data.services.SessionManagerService
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataModuleTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var firebaseApp: FirebaseApp

    @Mock
    lateinit var firebaseFirestore: FirebaseFirestore

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    @Mock
    lateinit var sessionManagerService: SessionManagerService

    @Mock
    lateinit var authService: AuthenticationService

    @Mock
    lateinit var userRepositoryImpl: UserRepositoryImpl

    @Mock
    lateinit var costsRepositoryImpl: CostsRepositoryImpl

    @Mock
    lateinit var homesRepositoryImpl: HomesRepositoryImpl

    @InjectMocks
    lateinit var dataModule: DataModule

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testProviderFirebase() {
        `when`(FirebaseApp.initializeApp(context)).thenReturn(firebaseApp)
        assertEquals(firebaseApp, dataModule.providerFirebase(context))
    }

    @Test
    fun testProviderFirebaseFirestore() {
        `when`(Firebase.firestore).thenReturn(firebaseFirestore)
        assertEquals(firebaseFirestore, dataModule.providerFirebaseFirestore())
    }

    @Test
    fun testProviderFirebaseAuth() {
        `when`(Firebase.auth).thenReturn(firebaseAuth)
        assertEquals(firebaseAuth, dataModule.providerFirebaseAuth())
    }

    @Test
    fun testProviderSessionManager() {
        assertEquals(
            sessionManagerService,
            dataModule.providerSessionManager(sessionManagerService)
        )
    }

    @Test
    fun testProviderAuthService() {
        assertEquals(authService, dataModule.providerAuthService(authService))
    }

    @Test
    fun testProviderUserRepository() {
        assertEquals(userRepositoryImpl, dataModule.providerUserRepository(userRepositoryImpl))
    }

    @Test
    fun testProviderCostsRepository() {
        assertEquals(costsRepositoryImpl, dataModule.providerCostsRepository(costsRepositoryImpl))
    }

    @Test
    fun testProviderHomesRepository() {
        assertEquals(homesRepositoryImpl, dataModule.providerHomesRepository(homesRepositoryImpl))
    }
}


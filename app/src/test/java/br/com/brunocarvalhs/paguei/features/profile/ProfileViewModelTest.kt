package br.com.brunocarvalhs.paguei.features.profile

import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    @Mock
    lateinit var fakeAuthentication: Authentication<AuthCredential>

    @Mock
    lateinit var fakeSessionManager: SessionManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testLogout() {
        runBlocking {
            Dispatchers.setMain(Dispatchers.Unconfined)
            `when`(fakeAuthentication.logout()).thenReturn(true)
            val viewModel = ProfileViewModel(fakeSessionManager, fakeAuthentication)
            viewModel.logout()
            verify(fakeSessionManager).logout()
        }
    }
}




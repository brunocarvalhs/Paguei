package br.com.brunocarvalhs.data.services

import br.com.brunocarvalhs.paguei.domain.entities.UserEntities
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SessionManagerServiceTest {

    @Mock
    private lateinit var user: UserEntities

    private lateinit var sessionManagerService: SessionManagerService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sessionManagerService = SessionManagerService()
    }

    @Test
    fun testIsLoggedIn() {
        assertFalse(sessionManagerService.isLoggedIn())
        sessionManagerService.login(user, "token")
        assertTrue(sessionManagerService.isLoggedIn())
    }

    @Test
    fun testLogin() {
        sessionManagerService.login(user, "token")
        assertEquals(user, sessionManagerService.getUser())
        assertEquals("token", sessionManagerService.getToken())
    }

    @Test
    fun testLogout() {
        sessionManagerService.login(user, "token")
        sessionManagerService.logout()
        assertFalse(sessionManagerService.isLoggedIn())
        assertEquals(null, sessionManagerService.getUser())
        assertEquals(null, sessionManagerService.getToken())
    }
}

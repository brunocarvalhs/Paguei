package br.com.brunocarvalhs.data.services

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SessionManagerServiceTest {

    @Mock
    private lateinit var user: UserEntities

    @Mock
    private lateinit var group: GroupEntities

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
        assertNull(sessionManagerService.getUser())
        assertNull(sessionManagerService.getToken())
    }

    @Test
    fun testSelectedGroup() {
        assertNull(sessionManagerService.getGroup())
        sessionManagerService.sessionGroup(group)
        assertNotNull(sessionManagerService.getGroup())
        sessionManagerService.sessionGroup(null)
        assertNull(sessionManagerService.getGroup())
    }
}

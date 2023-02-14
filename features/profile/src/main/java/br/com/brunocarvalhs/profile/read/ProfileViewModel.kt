package br.com.brunocarvalhs.profile.read

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authentication: Authentication<AuthCredential>
) : ViewModel() {
    val user = sessionManager.getUser()

    fun logout() {
        viewModelScope.launch {
            if (authentication.logout()) {
                sessionManager.logout()
            }
        }
    }
}
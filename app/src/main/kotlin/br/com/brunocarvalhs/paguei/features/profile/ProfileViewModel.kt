package br.com.brunocarvalhs.paguei.features.profile

import androidx.lifecycle.ViewModel
import br.com.brunocarvalhs.paguei.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    sessionManager: SessionManager
) : ViewModel() {
    val user = sessionManager.getUser()
}
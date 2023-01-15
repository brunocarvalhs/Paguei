package br.com.brunocarvalhs.payflow.features.profile

import androidx.lifecycle.ViewModel
import br.com.brunocarvalhs.payflow.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    val user = sessionManager.getUser()
}
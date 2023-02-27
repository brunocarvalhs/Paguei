package br.com.brunocarvalhs.profile.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.DataStore
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val dataStore: DataStore,
    private val sessionManager: SessionManager,
    private val authentication: Authentication,
    private val userRepository: UserRepository
) : ViewModel() {

    fun deleteData() {
        viewModelScope.launch {
            sessionManager.getUser()?.let {
                authentication.delete()
                userRepository.delete(it)
            }
        }
    }
}
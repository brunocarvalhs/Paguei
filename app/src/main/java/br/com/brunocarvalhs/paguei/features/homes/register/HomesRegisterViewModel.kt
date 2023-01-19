package br.com.brunocarvalhs.paguei.features.homes.register

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.HomesEntities
import br.com.brunocarvalhs.domain.repositories.HomesRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomesRegisterViewModel @Inject constructor(
    private val repository: HomesRepository, private val sessionManager: SessionManager
) : BaseViewModel<HomesRegisterViewState>() {

    var members = emptyList<String>()

    fun save(homes: HomesEntities) {
        defineMember()
        viewModelScope.launch {
            try {
                mutableState.value = HomesRegisterViewState.Loading
                repository.add(homes)
                mutableState.value = HomesRegisterViewState.Success
            } catch (error: Exception) {
                mutableState.value = HomesRegisterViewState.Error(error.message)
            }
        }
    }

    private fun defineMember() {
        members.plus(sessionManager.getUser()?.id)
    }
}
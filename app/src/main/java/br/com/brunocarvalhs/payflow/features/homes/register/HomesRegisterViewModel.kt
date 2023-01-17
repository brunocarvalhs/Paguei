package br.com.brunocarvalhs.payflow.features.homes.register

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.payflow.domain.entities.HomesEntities
import br.com.brunocarvalhs.payflow.domain.entities.UserEntities
import br.com.brunocarvalhs.payflow.domain.repositories.HomesRepository
import br.com.brunocarvalhs.payflow.domain.services.SessionManager
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

    fun addMember(member: UserEntities) {
        if (!members.contains(member.id)) members.plus(member.id)
    }
}
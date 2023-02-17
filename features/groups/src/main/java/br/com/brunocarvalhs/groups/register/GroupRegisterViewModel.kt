package br.com.brunocarvalhs.paguei.features.groups.register

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.repositories.HomesRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.groups.register.GroupRegisterViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRegisterViewModel @Inject constructor(
    private val repository: HomesRepository, private val sessionManager: SessionManager
) : BaseViewModel<GroupRegisterViewState>() {

    var members = emptyList<String>()

    fun save(homes: GroupEntities) {
        defineMember()
        viewModelScope.launch {
            try {
                mutableState.value = GroupRegisterViewState.Loading
                repository.add(homes)
                mutableState.value = GroupRegisterViewState.Success
            } catch (error: Exception) {
                mutableState.value = GroupRegisterViewState.Error(error.message)
            }
        }
    }

    private fun defineMember() {
        members.plus(sessionManager.getUser()?.id)
    }
}
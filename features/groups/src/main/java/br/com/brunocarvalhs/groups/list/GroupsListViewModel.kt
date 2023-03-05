package br.com.brunocarvalhs.groups.list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.groups.list.GroupsListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsListViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository,
    private val sessionManager: SessionManager,
) : BaseViewModel<GroupsListViewState>() {

    val user = sessionManager.getUser()

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = GroupsListViewState.Loading
                val homes = groupsRepository.list()
                mutableState.value = GroupsListViewState.Success(homes)
            } catch (error: Exception) {
                mutableState.value = GroupsListViewState.Error(error.message)
            }
        }
    }

    fun selected(home: GroupEntities? = null) {
        sessionManager.sessionGroup(home)
    }
}
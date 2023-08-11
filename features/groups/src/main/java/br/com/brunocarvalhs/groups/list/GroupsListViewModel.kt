package br.com.brunocarvalhs.groups.list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.GroupsModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.usecase.auth.GetGroupSessionUseCase
import br.com.brunocarvalhs.domain.usecase.auth.GetUserFromDatabaseSessionUseCase
import br.com.brunocarvalhs.domain.usecase.group.DeleteGroupUseCase
import br.com.brunocarvalhs.domain.usecase.group.FetchGroupsUseCase
import br.com.brunocarvalhs.domain.usecase.group.UpdateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsListViewModel @Inject constructor(
    private val getUserFromSessionUseCase: GetUserFromDatabaseSessionUseCase,
    private val fetchGroupsUseCase: FetchGroupsUseCase,
    private val getGroupSessionUseCase: GetGroupSessionUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
) : BaseViewModel<GroupsListViewState>() {

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = GroupsListViewState.Loading
                val user = getUserFromSessionUseCase.invoke().getOrThrow()
                val list = fetchGroupsUseCase.invoke().getOrThrow()
                mutableState.value = GroupsListViewState.Success(list, user)
            } catch (error: Exception) {
                mutableState.value = GroupsListViewState.Error(error.message)
            }
        }
    }

    fun selected(home: GroupEntities? = null) {
        viewModelScope.launch {
            getGroupSessionUseCase.invoke(home).onFailure { error ->
                mutableState.value = GroupsListViewState.Error(error.message)
            }
        }
    }

    fun exitMemberGroup(item: GroupEntities, function: () -> Unit) {
        viewModelScope.launch {
            mutableState.value = GroupsListViewState.Loading
            val user = getUserFromSessionUseCase.invoke().getOrNull()
            user?.let { userData ->
                if (item.members.contains(userData.id)) {
                    val group = (item as GroupsModel)
                        .copy(members = item.members.filter { it != userData.id })
                    if (group.members.isNotEmpty()) {
                        updateGroupUseCase.invoke(group)
                            .onSuccess { function.invoke() }
                            .onFailure { error ->
                                mutableState.value = GroupsListViewState.Error(error.message)
                            }
                    } else {
                        deleteGroupUseCase.invoke(group)
                            .onSuccess { function.invoke() }
                            .onFailure { error ->
                                mutableState.value = GroupsListViewState.Error(error.message)
                            }
                    }
                }
            }
        }
    }
}
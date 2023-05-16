package br.com.brunocarvalhs.groups.list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.usecase.auth.GetGroupSessionUseCase
import br.com.brunocarvalhs.domain.usecase.auth.GetUserFromDatabaseSessionUseCase
import br.com.brunocarvalhs.domain.usecase.group.FetchGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsListViewModel @Inject constructor(
    private val getUserFromSessionUseCase: GetUserFromDatabaseSessionUseCase,
    private val fetchGroupsUseCase: FetchGroupsUseCase,
    private val getGroupSessionUseCase: GetGroupSessionUseCase,
) : BaseViewModel<GroupsListViewState>() {

    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = GroupsListViewState.Loading
            getUserFromSessionUseCase.invoke()
                .onSuccess { mutableState.value = GroupsListViewState.SuccessUser(it) }
                .onFailure { mutableState.value = GroupsListViewState.Error(it.message) }
            fetchGroupsUseCase.invoke()
                .onSuccess { mutableState.value = GroupsListViewState.Success(it) }
                .onFailure { mutableState.value = GroupsListViewState.Error(it.message) }
        }
    }

    fun selected(home: GroupEntities? = null) {
        viewModelScope.launch {
            getGroupSessionUseCase.invoke(home)
                .onFailure { error ->
                    mutableState.value = GroupsListViewState.Error(error.message)
                }
        }
    }
}
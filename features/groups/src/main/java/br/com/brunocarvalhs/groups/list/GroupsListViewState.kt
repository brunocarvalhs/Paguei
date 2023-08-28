package br.com.brunocarvalhs.groups.list

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities

sealed class GroupsListViewState {
    object Loading : GroupsListViewState()
    data class Success(val list: List<GroupEntities>, val user: UserEntities?) :
        GroupsListViewState()

    data class Error(val error: String?) : GroupsListViewState()
}

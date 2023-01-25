package br.com.brunocarvalhs.paguei.features.groups.list

import br.com.brunocarvalhs.domain.entities.GroupEntities

sealed class GroupsListViewState {
    object Loading : GroupsListViewState()
    data class Success(val list: List<GroupEntities>) : GroupsListViewState()
    data class Error(val error: String?) : GroupsListViewState()
}

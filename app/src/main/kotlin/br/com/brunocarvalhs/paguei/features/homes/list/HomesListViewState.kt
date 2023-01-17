package br.com.brunocarvalhs.paguei.features.homes.list

import br.com.brunocarvalhs.paguei.domain.entities.HomesEntities

sealed class HomesListViewState {
    object Loading : HomesListViewState()
    data class Success(val list: List<HomesEntities>) : HomesListViewState()
    data class Error(val error: String?) : HomesListViewState()
}

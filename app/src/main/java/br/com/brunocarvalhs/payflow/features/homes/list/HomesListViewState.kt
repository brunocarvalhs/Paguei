package br.com.brunocarvalhs.payflow.features.homes.list

sealed class HomesListViewState {
    object Loading : HomesListViewState()
    data class Success(val list: List<String>) : HomesListViewState()
    data class Error(val error: String?) : HomesListViewState()
}

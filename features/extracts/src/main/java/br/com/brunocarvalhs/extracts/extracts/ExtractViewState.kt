package br.com.brunocarvalhs.extracts.extracts

import br.com.brunocarvalhs.domain.entities.CostEntities

sealed class ExtractViewState {
    object Loading : ExtractViewState()
    data class Success(val list: List<CostEntities>) : ExtractViewState()
    data class Error(val message: String?) : ExtractViewState()
}

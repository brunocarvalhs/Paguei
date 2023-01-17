package br.com.brunocarvalhs.payflow.features.costs.extracts

import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities

sealed class ExtractViewState {
    object Loading : ExtractViewState()
    data class Success(val list: List<CostsEntities>) : ExtractViewState()
    data class Error(val message: String?) : ExtractViewState()
}

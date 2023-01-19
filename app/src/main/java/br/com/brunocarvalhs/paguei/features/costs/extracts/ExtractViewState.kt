package br.com.brunocarvalhs.paguei.features.costs.extracts

import br.com.brunocarvalhs.domain.entities.CostsEntities

sealed class ExtractViewState {
    object Loading : ExtractViewState()
    data class Success(val list: List<CostsEntities>) : ExtractViewState()
    data class Error(val message: String?) : ExtractViewState()
}

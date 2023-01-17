package br.com.brunocarvalhs.paguei.features.costs.costs_list

import br.com.brunocarvalhs.paguei.domain.entities.CostsEntities

sealed class CostsViewState {
    object Loading : CostsViewState()
    data class Success(val list: List<CostsEntities>) : CostsViewState()
    data class Error(val message: String?) : CostsViewState()
}

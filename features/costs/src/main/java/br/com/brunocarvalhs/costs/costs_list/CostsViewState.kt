package br.com.brunocarvalhs.costs.costs_list

import br.com.brunocarvalhs.domain.entities.CostEntities

sealed class CostsViewState {
    object Loading : CostsViewState()
    data class Success(val list: List<CostEntities>) : CostsViewState()
    data class Error(val message: String?) : CostsViewState()
}

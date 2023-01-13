package br.com.brunocarvalhs.payflow.features.home.costs

import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities

sealed class CostsViewState {
    object Loading : CostsViewState()
    data class Success(val list: List<CostsEntities>) : CostsViewState()
    data class Error(val message: String?) : CostsViewState()
}

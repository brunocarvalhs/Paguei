package br.com.brunocarvalhs.costs.reader_cost

import br.com.brunocarvalhs.domain.entities.CostEntities

sealed class CostReaderViewState {
    object Loading : CostReaderViewState()
    data class Success(val cost: CostEntities) : CostReaderViewState()
    data class Error(val error: String?) : CostReaderViewState()
}

package br.com.brunocarvalhs.paguei.features.costs.reader_cost

import br.com.brunocarvalhs.paguei.domain.entities.CostsEntities

sealed class CostReaderViewState {
    object Loading : CostReaderViewState()
    data class Success(val cost: CostsEntities) : CostReaderViewState()
    data class Error(val error: String?) : CostReaderViewState()
}

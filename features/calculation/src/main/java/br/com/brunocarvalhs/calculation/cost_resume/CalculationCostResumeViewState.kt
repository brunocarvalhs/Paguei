package br.com.brunocarvalhs.calculation.cost_resume

import br.com.brunocarvalhs.domain.entities.CostEntities

sealed class CalculationCostResumeViewState {
    object Loading : CalculationCostResumeViewState()
    data class Success(val list: List<CostEntities>) : CalculationCostResumeViewState()
    data class Error(val message: String?) : CalculationCostResumeViewState()
}

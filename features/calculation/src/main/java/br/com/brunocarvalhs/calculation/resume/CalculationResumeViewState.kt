package br.com.brunocarvalhs.calculation.resume

import br.com.brunocarvalhs.domain.entities.UserEntities

sealed class CalculationResumeViewState {
    object Loading : CalculationResumeViewState()
    data class Success(val percentagesToMembers: HashMap<UserEntities, Double>) :
        CalculationResumeViewState()

    data class Error(val message: String?) : CalculationResumeViewState()
}

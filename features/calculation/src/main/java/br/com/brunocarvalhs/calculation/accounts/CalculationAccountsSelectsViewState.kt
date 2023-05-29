package br.com.brunocarvalhs.calculation.accounts

import br.com.brunocarvalhs.domain.entities.UserEntities

sealed class CalculationAccountsSelectsViewState {
    object Loading : CalculationAccountsSelectsViewState()
    data class Success(val list: List<UserEntities>) : CalculationAccountsSelectsViewState()
    data class Error(val error: String?) : CalculationAccountsSelectsViewState()
}
package br.com.brunocarvalhs.calculation.accounts

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.EMPTY_MONEY
import br.com.brunocarvalhs.commons.utils.formatDecimal
import br.com.brunocarvalhs.commons.utils.orEmpty
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.GetUserForIdUseCase
import br.com.brunocarvalhs.domain.usecase.cost.FetchCostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculationAccountsSelectsViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val getUserForIdUseCase: GetUserForIdUseCase,
    private val useCase: FetchCostsUseCase,
) : BaseViewModel<CalculationAccountsSelectsViewState>() {

    private val group = sessionManager.getGroup()

    var listMembers: List<String> = emptyList()
    var listMembersSelected: List<String> = emptyList()
    var listCosts: List<String> = emptyList()
    val totalSalary = ObservableField(EMPTY_MONEY)

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CalculationAccountsSelectsViewState.Loading
                val members = group?.members?.map { id ->
                    getUserForIdUseCase.invoke(id).getOrThrow()
                }

                members?.let {
                    listCosts = useCase.invoke().getOrThrow().map { cost -> cost.toJson() }
                    listMembers = members.map { it?.toJson().orEmpty() }
                    totalSalary.set(calculationTotalSalaries(members))
                    listMembersSelected = listMembers
                    mutableState.value =
                        CalculationAccountsSelectsViewState.Success(members.mapNotNull { it })
                }
            } catch (e: Exception) {
                mutableState.value = CalculationAccountsSelectsViewState.Error(e.message)
            }
        }
    }

    fun replaceCalculation(members: List<UserEntities>) {
        val result = calculationTotalSalaries(members)
        totalSalary.set(result)
        listMembersSelected = members.map { cost -> cost.toJson() }
    }

    private fun calculationTotalSalaries(members: List<UserEntities?>) =
        members.sumOf { it?.salary?.toDouble().orEmpty() }.formatDecimal()
}
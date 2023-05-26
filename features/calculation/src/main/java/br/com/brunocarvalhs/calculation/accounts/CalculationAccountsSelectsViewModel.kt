package br.com.brunocarvalhs.calculation.accounts

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
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
    var listCosts: List<String> = emptyList()
    val totalSalary = ObservableField<String>("0,00")

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CalculationAccountsSelectsViewState.Loading
                val members = group?.members?.map { id ->
                    getUserForIdUseCase.invoke(id).getOrThrow()
                }

                members?.let { members ->
                    listCosts = useCase.invoke().getOrThrow().map { cost -> cost.toJson() }
                    listMembers = members.map { it?.toJson().orEmpty() }
                    totalSalary.set(
                        String.format(
                            "%.2f",
                            members.sumOf { it?.salary?.toDouble() ?: 0.0 })
                    )
                    mutableState.value = CalculationAccountsSelectsViewState.Success(members)
                }
            } catch (e: Exception) {
                mutableState.value = CalculationAccountsSelectsViewState.Error(e.message)
            }
        }
    }

    fun replaceCalculation(members: List<UserEntities?>) {
        val result = String.format(
            "%.2f",
            members.sumOf { it?.salary?.toDouble() ?: 0.0 })

        totalSalary.set(result)
    }
}
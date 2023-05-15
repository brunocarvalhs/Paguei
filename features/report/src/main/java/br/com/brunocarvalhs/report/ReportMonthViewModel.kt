package br.com.brunocarvalhs.report

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.report.CalculateExpenseFrequencyUseCase
import br.com.brunocarvalhs.domain.usecase.report.CalculateExpensesByCategoryUseCase
import br.com.brunocarvalhs.domain.usecase.report.CalculateMonthlyExpensesUseCase
import br.com.brunocarvalhs.domain.usecase.report.CalculatePaymentPromptnessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReportMonthViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val calculateExpenseFrequencyUseCase: CalculateExpenseFrequencyUseCase,
    private val calculateExpensesByCategoryUseCase: CalculateExpensesByCategoryUseCase,
    private val calculateMonthlyExpensesUseCase: CalculateMonthlyExpensesUseCase,
) : BaseViewModel<ReportMonthViewState>() {

    var list: List<CostEntities> = emptyList()

    var month: String? = null

    var totalCosts: String? = null
        private set

    var totalPay: String? = null
        private set

    var frequency: Map<String, Int>? = null
        private set

    var totalRender: String? = sessionManager.getUser()?.salary
        private set

    fun fetchData() {
        mutableState.value = ReportMonthViewState.Loading
        this.calculatePaymentPromptness()
        this.calculateMonthlyExpenses()
        this.calculateExpenseFrequency()
        this.calculateTotalPay()
        this.calculateTotalCosts()
        mutableState.value = ReportMonthViewState.Success
    }

    private fun calculateTotalCosts() {
        viewModelScope.launch {
            val total = list.map {
                it.value?.toDouble()
            }
            val result = total.filterNotNull().sumOf { it }
            totalCosts = formattedDecimal(result)
        }
    }

    private fun calculateTotalPay() {
        viewModelScope.launch {
            val total = list.filter { it.paymentVoucher != null }.map {
                it.value?.toDouble()
            }
            val result = total.filterNotNull().sumOf { it }
            totalPay = formattedDecimal(result)
        }
    }

    private fun formattedDecimal(value: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2
        return numberFormat.format(value)
    }

    private fun calculateExpenseFrequency() {
        // Utilize o calculateExpenseFrequencyUseCase para calcular a frequência das despesas
        viewModelScope.launch {
            frequency = calculateExpenseFrequencyUseCase.invoke(list)
        }
    }

    fun calculateExpensesByCategory() {
        // Utilize o calculateExpensesByCategoryUseCase para calcular as despesas por categoria
        viewModelScope.launch {
            val result = calculateExpensesByCategoryUseCase.invoke(list)
        }
    }

    private fun calculateMonthlyExpenses() {
        // Utilize o calculateMonthlyExpensesUseCase para calcular as despesas mensais
        viewModelScope.launch {
            month?.let { month ->
                val result = calculateMonthlyExpensesUseCase.invoke(list, month)
            }
        }
    }

    private fun calculatePaymentPromptness() {
        // Utilize o calculatePaymentPromptnessUseCaseFactory para calcular a pontualidade de pagamento
        viewModelScope.launch {
//            val result = calculatePaymentPromptnessUseCase.invoke(list, Date(month))
        }
    }

}
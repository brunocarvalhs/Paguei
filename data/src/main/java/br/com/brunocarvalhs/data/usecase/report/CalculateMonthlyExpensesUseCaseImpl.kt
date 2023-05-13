package br.com.brunocarvalhs.data.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.report.CalculateMonthlyExpensesUseCase

class CalculateMonthlyExpensesUseCaseImpl : CalculateMonthlyExpensesUseCase {
    override suspend fun invoke(costs: List<CostEntities>, referenceMonth: String): Double {
        val costsOfMonth = costs.filter { it.dateReferenceMonth == referenceMonth }
        return costsOfMonth.mapNotNull { it.value }.sumOf { it.toDouble() }
    }
}
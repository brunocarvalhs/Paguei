package br.com.brunocarvalhs.domain.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities

interface CalculateMonthlyExpensesUseCase {
    suspend operator fun invoke(costs: List<CostEntities>, referenceMonth: String): Double
}